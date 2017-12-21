package ch.makery.address.graphs;

import java.awt.BorderLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;



/**
 * Manages the grid region and all charts on the screen.
 * 
 * Users can click-and-drag in this region to create new charts or interact with existing charts.
 */
@SuppressWarnings("serial")
public class OpenGLChartsRegion extends JPanel {
	
	Animator animator;
	int canvasWidth;
	int canvasHeight;
	
	// grid size
	int columnCount;
	int rowCount;
	
	// grid locations for the opposite corners of where a new chart will be placed
	int startX;
	int startY;
	int endX;
	int endY;
	
	// time and zoom settings
	boolean liveView;
	int nonLiveViewSamplesCount;
	double zoomLevel;
	
	// mouse pointer's current location (pixels, origin at bottom-left)
	int mouseX;
	int mouseY;
	PositionedChart chartToCloseOnClick;
	
	public OpenGLChartsRegion() {
		
		super();
		
		columnCount = Controller.getGridColumns();
		rowCount    = Controller.getGridRows();
		
		startX  = -1;
		startY  = -1;
		endX    = -1;
		endY    = -1;
		
		liveView = true;
		nonLiveViewSamplesCount = 0;
		zoomLevel = 1;
		
		mouseX = -1;
		mouseY = -1;
		chartToCloseOnClick = null;
		
		GLCanvas glCanvas = new GLCanvas(new GLCapabilities(GLProfile.get(GLProfile.GL2)));
		glCanvas.addGLEventListener(new GLEventListener() {

			@Override public void init(GLAutoDrawable drawable) {
				
				GL2 gl = drawable.getGL().getGL2();
				
				gl.glEnable(GL2.GL_BLEND);
				gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
				gl.glEnable(GL2.GL_POINT_SMOOTH);
			    gl.glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_FASTEST);
				gl.glEnable(GL2.GL_LINE_SMOOTH);
			    gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_FASTEST);
//				gl.glEnable(GL2.GL_POLYGON_SMOOTH);
//			    gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_FASTEST);
			    
				gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
			    
				gl.glLineWidth(Theme.lineWidth);
				gl.glPointSize(Theme.pointSize);
				
				gl.setSwapInterval(1);
				
			}
						
			@Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
				
				GL2 gl = drawable.getGL().getGL2();
				
				gl.glMatrixMode(GL2.GL_PROJECTION);
				gl.glLoadIdentity();
				gl.glOrtho(0, width, 0, height, -100000, 100000);
				
				canvasWidth = width;
				canvasHeight = height;
				
			}

			@Override public void display(GLAutoDrawable drawable) {
				
				int columnWidth = canvasWidth  / columnCount;
				int rowHeight   = canvasHeight / rowCount;
				int gridWidth   = columnWidth * columnCount;
				int gridHeight  = rowHeight   * rowCount;
				int gridYoffset = canvasHeight - gridHeight;
				
				// prepare OpenGL
				GL2 gl = drawable.getGL().getGL2();
				
				gl.glMatrixMode(GL2.GL_MODELVIEW);
				gl.glLoadIdentity();
				
				gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
				gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
				
				gl.glLineWidth(Theme.lineWidth);
				gl.glPointSize(Theme.pointSize);

				// draw a neutral background
				float[] backgroundColor = new float[] {getBackground().getRed() / 255.0f, getBackground().getGreen() / 255.0f, getBackground().getBlue() / 255.0f};
				gl.glBegin(GL2.GL_QUADS);
				gl.glColor3fv(backgroundColor, 0);
					gl.glVertex2f(0,           0);
					gl.glVertex2f(0,           canvasHeight);
					gl.glVertex2f(canvasWidth, canvasHeight);
					gl.glVertex2f(canvasWidth, 0);
				gl.glEnd();
				
				// draw the grid background
				gl.glBegin(GL2.GL_QUADS);
				gl.glColor3fv(Theme.gridBackgroundColor, 0);
					gl.glVertex2f(0,         0 + gridYoffset);
					gl.glVertex2f(0,         gridHeight + gridYoffset);
					gl.glVertex2f(gridWidth, gridHeight + gridYoffset);
					gl.glVertex2f(gridWidth, 0 + gridYoffset);
				gl.glEnd();
				
				// draw vertical grid lines
				gl.glBegin(GL2.GL_LINES);
				gl.glColor3fv(Theme.gridLinesColor, 0);
					for(int i = 1; i < columnCount; i++) {
						gl.glVertex2f(columnWidth * i, 0 + gridYoffset);
						gl.glVertex2f(columnWidth * i, gridHeight + gridYoffset);
					}
				gl.glEnd();
				
				// draw horizontal grid lines
				gl.glBegin(GL2.GL_LINES);
				gl.glColor3fv(Theme.gridLinesColor, 0);
					for(int i = 1; i < rowCount; i++) {
						gl.glVertex2f(0,         rowHeight * i + gridYoffset);
						gl.glVertex2f(gridWidth, rowHeight * i + gridYoffset);
					}
				gl.glEnd();
				
				// draw bounding box where the user is actively clicking-and-dragging to place a new chart
				gl.glBegin(GL2.GL_QUADS);
				gl.glColor3fv(Theme.gridBoxColor, 0);
					int x1 = startX < endX ? startX * columnWidth : endX * columnWidth;
					int y1 = startY < endY ? startY * rowHeight   : endY * rowHeight;
					int x2 = x1 + (Math.abs(endX - startX) + 1) * columnWidth;
					int y2 = y1 + (Math.abs(endY - startY) + 1) * rowHeight;
					y1 = canvasHeight - y1;
					y2 = canvasHeight - y2;
					gl.glVertex2f(x1, y1);
					gl.glVertex2f(x1, y2);
					gl.glVertex2f(x2, y2);
					gl.glVertex2f(x2, y1);
				gl.glEnd();
				
				// draw the charts
				//
				// the modelview matrix is translated so the origin will be at the bottom-left for each chart.
				// the scissor test is used to clip rendering to the region allocated for each chart.
				// if charts will be using off-screen framebuffers, they need to disable the scissor test when (and only when) drawing off-screen.
				// after the chart is drawn with OpenGL, any text queued for rendering will be drawn on top.
				List<PositionedChart> charts = Controller.getCharts();
				if(charts.size() == 0)
					liveView = true;
				int lastSampleNumber = liveView ? Controller.getSamplesCount() - 1 : nonLiveViewSamplesCount;
				PositionedChart chartToClose = null;
				gl.glEnable(GL2.GL_SCISSOR_TEST);
				for(PositionedChart chart : charts) {
					int width = columnWidth * (chart.bottomRightX - chart.topLeftX + 1);
					int height = rowHeight * (chart.bottomRightY - chart.topLeftY + 1);
					int xOffset = chart.topLeftX * columnWidth;
					int yOffset = chart.topLeftY * rowHeight;
					yOffset = canvasHeight - yOffset - height;
					gl.glScissor(xOffset, yOffset, width, height);
					gl.glPushMatrix();
					gl.glTranslatef(xOffset, yOffset, 0);
					FontUtils.setOffsets(xOffset, yOffset);
					chart.drawChart(gl, width, height, lastSampleNumber, zoomLevel);
					FontUtils.drawQueuedText(gl, canvasWidth, canvasHeight);
					
					boolean mouseOverThisChart = mouseX >= xOffset && mouseX <= xOffset + width && mouseY >= yOffset && mouseY <= yOffset + height;
					if(mouseOverThisChart) {
						float iconWidth = 15f * Controller.getDisplayScalingFactor();
						float inset = iconWidth * 0.2f;
						float closeIconX1 = xOffset + width - iconWidth;
						float closeIconX2 = xOffset + width;
						float closeIconY1 = yOffset + height - iconWidth;
						float closeIconY2 = yOffset + height;
						boolean mouseOverCloseIcon = mouseX >= closeIconX1 && mouseX <= closeIconX2 && mouseY >= closeIconY1 && mouseY <= closeIconY2;
						if(mouseOverCloseIcon)
							chartToClose = chart;
						gl.glBegin(GL2.GL_QUADS);
							if(mouseOverCloseIcon)
								gl.glColor4f(0, 0, 0, 1);
							else
								gl.glColor4f(1, 1, 1, 1);
							gl.glVertex2f(width,             height);
							gl.glVertex2f(width,             height - iconWidth);
							gl.glVertex2f(width - iconWidth, height - iconWidth);
							gl.glVertex2f(width - iconWidth, height);
						gl.glEnd();
						gl.glBegin(GL2.GL_LINE_LOOP);
							if(mouseOverCloseIcon)
								gl.glColor4f(1, 1, 1, 1);
							else
								gl.glColor4f(0, 0, 0, 1);
							gl.glVertex2f(width,             height);
							gl.glVertex2f(width,             height - iconWidth);
							gl.glVertex2f(width - iconWidth, height - iconWidth);
							gl.glVertex2f(width - iconWidth, height);
						gl.glEnd();
						gl.glBegin(GL2.GL_LINES);
							if(mouseOverCloseIcon)
								gl.glColor4f(1, 1, 1, 1);
							else
								gl.glColor4f(0, 0, 0, 1);
							gl.glVertex2f(width - inset, height - inset);
							gl.glVertex2f(width - iconWidth + inset, height - iconWidth + inset);
							gl.glVertex2f(width - iconWidth + inset, height - inset);
							gl.glVertex2f(width - inset, height - iconWidth + inset);
						gl.glEnd();
					}
					
					gl.glPopMatrix();
				}
				gl.glDisable(GL2.GL_SCISSOR_TEST);
				chartToCloseOnClick = chartToClose;
				
//				System.out.println(String.format("%2.2fFPS, %2dms", animator.getLastFPS(), animator.getLastFPSPeriod()));
				
			}
			
			@Override public void dispose(GLAutoDrawable drawable) {
				
			}
			
		});
		
		setLayout(new BorderLayout());
		add(glCanvas, BorderLayout.CENTER);
	
		animator = new Animator(glCanvas);
		animator.setUpdateFPSFrames(1, null);
		animator.start();
		
		glCanvas.addMouseListener(new MouseListener() {
			
			// the mouse was pressed, attempting to start a new chart region
			@Override public void mousePressed(MouseEvent me) {
				
				int proposedStartX = me.getX() * columnCount / getWidth();
				int proposedStartY = me.getY() * rowCount / getHeight();
				
				if(proposedStartX < columnCount && proposedStartY < rowCount && Controller.gridRegionAvailable(proposedStartX, proposedStartY, proposedStartX, proposedStartY)) {
					startX = endX = proposedStartX;
					startY = endY = proposedStartY;
				}
				
			}
			
			// the mouse was released, attempting to create a new chart
			@Override public void mouseReleased(MouseEvent me) {

				if(endX == -1 || endY == -1)
					return;
			
				int proposedEndX = me.getX() * columnCount / getWidth();
				int proposedEndY = me.getY() * rowCount / getHeight();
				
				if(proposedEndX < columnCount && proposedEndY < rowCount && Controller.gridRegionAvailable(startX, startY, proposedEndX, proposedEndY)) {
					endX = proposedEndX;
					endY = proposedEndY;
				}
				
				JFrame parentWindow = (JFrame) SwingUtilities.windowForComponent(OpenGLChartsRegion.this);
				new AddChartWindow(parentWindow, startX, startY, endX, endY);
				
				startX = startY = -1;
				endX   = endY   = -1;
				
			}

			// the mouse left the canvas, no longer need to show the chart close icon
			@Override public void mouseExited (MouseEvent me) {
				
				mouseX = -1;
				mouseY = -1;
				
			}
			
			// the user clicked, possibly to close a chart
			@Override public void mouseClicked(MouseEvent me) {
				
				if(chartToCloseOnClick != null)
					Controller.removeChart(chartToCloseOnClick);
				
			}
			
			@Override public void mouseEntered(MouseEvent me) { }
			
		});
		
		glCanvas.addMouseMotionListener(new MouseMotionListener() {
			
			// the mouse was dragged while attempting to create a new chart
			@Override public void mouseDragged(MouseEvent me) {
				
				if(endX == -1 || endY == -1)
					return;
				
				int proposedEndX = me.getX() * columnCount / getWidth();
				int proposedEndY = me.getY() * rowCount / getHeight();
				
				if(proposedEndX < columnCount && proposedEndY < rowCount && Controller.gridRegionAvailable(startX, startY, proposedEndX, proposedEndY)) {
					endX = proposedEndX;
					endY = proposedEndY;
				}
				
			}
			
			// log the mouse position so a chart close icon can be drawn
			@Override public void mouseMoved(MouseEvent me) {
				
				mouseX = me.getX();
				mouseY = glCanvas.getHeight() - me.getY();
				
			}
			
		});
		
		glCanvas.addMouseWheelListener(new MouseWheelListener() {
			
			// the mouse wheel was scrolled
			@Override public void mouseWheelMoved(MouseWheelEvent mwe) {

				double scrollAmount = mwe.getPreciseWheelRotation();
				double samplesPerScroll = Controller.getSampleRate() / 4;
				double zoomPerScroll = 0.1;
				float  displayScalingPerScroll = 0.1f;
				
				if(Controller.getCharts().size() == 0 && mwe.isShiftDown() == false)
					return;
				
				if(scrollAmount == 0)
					return;
				
				if(mwe.isControlDown() == false && mwe.isShiftDown() == false) {
					
					// no modifiers held down, so we're timeshifting
					if(liveView == true) {
						liveView = false;
						nonLiveViewSamplesCount = (Controller.getSamplesCount() - 1);
					}
					
					double delta = scrollAmount * samplesPerScroll * zoomLevel;
					if(delta < -0.5 || delta > 0.5)
						delta = Math.round(delta);
					else if(delta < 0)
						delta = -1;
					else if(delta >= 0)
						delta = 1;
					nonLiveViewSamplesCount += delta;
					
					if(nonLiveViewSamplesCount >= Controller.getSamplesCount() - 1)
						liveView = true;
				
				} else if(mwe.isControlDown() == true) {
					
					// ctrl is down, so we're zooming
					zoomLevel *= 1 + (scrollAmount * zoomPerScroll);
					
					if(zoomLevel > 1)
						zoomLevel = 1;
					else if(zoomLevel < 0)
						zoomLevel = Double.MIN_VALUE;
					
				} else if(mwe.isShiftDown() == true) {
					
					// shift is down, so we're setting the display scaling factor
					float newFactor = Controller.getDisplayScalingFactor() * (1 - ((float)scrollAmount * displayScalingPerScroll));
					Controller.setDisplayScalingFactor(newFactor);
					
				}
				
			}
			
		});
		
		// update the column and row counts when they change
		Controller.addGridChangedListener(new GridChangedListener() {
			@Override public void gridChanged(int columns, int rows) {
				columnCount = columns;
				rowCount = rows;
			}
		});
		
	}
	
}