import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 1/29/15
 * Dr. Youming Li
 * Computer Graphics
 *
 */
public class Screen extends JPanel {
    private int verticalLines = 80, horizontalLines = 60;
    private int pixelSize = 3;
    public static void main(String[] args){
        new Screen();
    }

    public Screen(){
        JFrame contentFrame = new JFrame("Screen Simulator");
        GroupLayout layout = new GroupLayout(contentFrame.getContentPane());
        JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        final JTextField width = new JTextField("80");
        final JTextField height = new JTextField("60");
        final JSlider radius = new JSlider(1, 25, 3);
        radius.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider radius = (JSlider) changeEvent.getSource();
                pixelSize = radius.getValue();
                repaint();
            }
        });
        radius.setMajorTickSpacing(1);
        radius.setMinorTickSpacing(1);
        radius.setPaintTicks(true);
        radius.setPaintLabels(true);

        JButton redraw = new JButton("Redraw");
        redraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateSize(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
            }
        });
        contentFrame.setSize(new Dimension(800, 600));
        contentFrame.setLocation(0, 0);

        JLabel x = new JLabel("x");
        JLabel widthLabel = new JLabel("width");
        JLabel heighLabel = new JLabel("height");
        JLabel radiusLabel = new JLabel("pixel radius");

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(scrollPane)
                        .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup()
                                                        .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(widthLabel)
                                                                        .addComponent(width, 25, 50, 150)
                                                                        .addComponent(x)
                                                                        .addComponent(heighLabel)
                                                                        .addComponent(height, 25, 50, 150)
                                                        )
                                                        .addComponent(radiusLabel)
                                                        .addComponent(radius)
                                        )
                                        .addComponent(redraw)
                        )

        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(scrollPane)
                        .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup()
                                                                        .addComponent(widthLabel)
                                                                        .addComponent(width, 5, 10, 20)
                                                                        .addComponent(x)
                                                                        .addComponent(heighLabel)
                                                                        .addComponent(height, 5, 10, 20)
                                                        )
                                                        .addComponent(radiusLabel)
                                                        .addComponent(radius)
                                        )
                                        .addComponent(redraw)
                        )

        );

        contentFrame.getContentPane().setLayout(layout);
        contentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentFrame.setVisible(true);
    }

    public void updateSize(int width, int height){
        verticalLines = width;
        horizontalLines = height;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1600,900);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(177, 177, 177));
        // lineOffsets contains the value of the vertical gap between lines and the horizontal gap between lines
        // in that order
        int[] lineOffsets = drawGridLines(g2, this.verticalLines, this.horizontalLines, 1600, 900);
//        drawLine(g2,0,0,50,300,lineOffsets);
//        drawLine(g2,0,10,50,10,lineOffsets);
//        drawLine(g2, 10, 0, 100, 0, lineOffsets);
//        drawLine(g2, 10, 50, 300, 80, lineOffsets);
//        drawLine(g2, 30, 10, 505,300, lineOffsets);
        int[] x = {0, 0, 10, 10, 0};
        int[] y = {0, 10, 10, 0, 0};
//        drawPolygon(g2, x, y, Color.black, lineOffsets );
        int[] x1 = {0, 0, 20, 10, 5, 0};
        int[] y1 = {0, 20, 25, 5, 0, 0};
        drawPolygon(g2, x1, y1, Color.black, lineOffsets );
//        drawLine(g2, 10, 0, 0, 0, lineOffsets);
//        drawLine(g2, 10,10, 10, 0, lineOffsets);
    }

    /**
     * Draws a line from the first specified point to the second specified point.
     *
     * @param g2 the graphics component to draw on
     * @param x0 the x coordinate of the first point
     * @param y0 the y coordinate of the first point
     * @param x1 the x coordinate of the second point
     * @param y1 the y coordinate of the second point
     * @param lineOffsets an array containing the horizontal and vertical offsets of the gridlines
     */
    private void drawLine(Graphics2D g2, int x0, int y0, int x1, int y1, int[] lineOffsets){
        if(x0 > x1) {
            // swap x0 and x1
            int tmp = x0;
            x0 = x1;
            x1 = tmp;
            // swap y0 and y1
            tmp = y0;
            y0 = y1;
            y1 = tmp;
        }
        if(y0 > y1){
            // swap y0 and y1
            int tmp = y0;
            y0 = y1;
            y1 = tmp;

            // swap x0 and x1
            tmp = x0;
            x0 = x1;
            x1 = tmp;
        }
        int dx = x1 - x0, dy=y1-y0;
        int d=2*(dy-dx);
        int incrE=2*dy, incrNE=2*(dy-dx);
        int x=x0, y=y0;

        writePixel(g2, x, y, Color.red, lineOffsets);
        while (x < x1){
            if (d <= 0) {
                x++;
                d += incrE;
            }
            else {
                x++;
                y++;
                d += incrNE;
            }
            writePixel(g2, x, y, Color.red, lineOffsets);
        }
        // Draw vertical lines
        while (y <= y1) {
            writePixel(g2, x, y, Color.red, lineOffsets);
            y++;
        }


    }

    private void drawPolygon(Graphics2D g2, int[] x, int[] y, Color color, int[] lineOffsets) {
        double[][] edges = new double[x.length][4];
        int y0, y1, x0, x1;
        for (int pos = 0; pos < x.length - 1; pos++) {
            y0 = y[pos];
            y1 = y[pos + 1];
            x0 = x[pos];
            x1 = x[pos + 1];

            // If y0 greater than y1 return y1 position else return y0 position
            int minYPos = y0 > y1 ? pos + 1 : pos;
            int maxY = y0 > y1 ? y0 : y1;
            edges[pos][0] = y[minYPos];
            edges[pos][1] = maxY;
            edges[pos][2] = x[minYPos];
            double dy = y1 > y0 ? (double)y1 - (double)y0 : (double)y0 - (double) y1;
            double dx = x1 > x0 ? (double)x1 - (double)x0 : (double)x0 - (double)x1;
            if( dx > 0)
                edges[pos][3] = dx / dy;
            else
                edges[pos][3] = 0;
            drawLine(g2, x0, y0, x1, y1, lineOffsets);
        }

        int j;
        int lengthOfGlobalEdges = 0;
        int[] posForGlobal = new int[edges.length];
        int k = 0;
        for (int i = 0; i < edges.length; i++) {
            System.out.println("i: " + i + " " + (1.0/edges[i][3]));
            if(Math.abs(edges[i][3]) >= 0.0) {
                if(!Double.isInfinite(edges[i][3])) {
                    posForGlobal[k] = i;
                    k++;
                    lengthOfGlobalEdges++;
                }else{
                    posForGlobal[k] = -1;
                    k++;
                }
            }
            else{
                posForGlobal[k] = -1;
                k++;
            }
        }

        double[][] globalEdges = new double[lengthOfGlobalEdges][4];
        j = 0;
        for(int i = 0; i<globalEdges.length; i++){
            while (j < posForGlobal.length){
                if(posForGlobal[j] != -1) {
                    globalEdges[i] = edges[posForGlobal[j]];
                    j++;
                    break;
                }
                j++;
            }
        }

        double key;
        for (int i = 0; i < globalEdges.length; i ++){
            key = globalEdges[i][0];
            j = i - 1;
            while (j >= 0 && globalEdges[j][0] > key){
                double[] tmp = globalEdges[j];
                globalEdges[j] = globalEdges[j+1];
                globalEdges[j+1] = tmp;
                j = j-1;
            }
            globalEdges[j+1][0] = key;
        }
        return;
    }

//    public int[][] getEdgesTable(int[] x, int[] y){
//
//
//        int size = x.length;
//        int[] xCoords = x;
//        int[] yCoords = y;
//        int scanLine = getMinY(y); //will be intialized to lowest y value
//        int[][] edges = new int[4][size];
//
//        boolean parity = false;
//
//        for (int i = 0; i < size; i ++){
//
//            edges[i][0] = Math.min(yCoords[i], yCoords[i+1]);
//            int tempXValIndex = checkMinIndex(yCoords[i], i, yCoords[i+1], i+1);
//            edges[i][1] = Math.max(yCoords[i], yCoords[i+1]);
//            edges[i][2] = xCoords[tempXValIndex];
//            edges[i][3] = ((xCoords[i] - xCoords[i+1]) / (yCoords[i] - yCoords[i+1]));
//
//        }
//
//        return edges;
//    }

    private int minPosition(int[] arr) {
        int minPos = 0;
        int minValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (minValue > arr[i]) {
                minPos = i;
                minValue = arr[i];
            }
        }
        return minPos;

    }
    private int maxPosition(int[] arr) {
        int maxPos = 0;
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (maxValue < arr[i]) {
                maxPos = i;
                maxValue = arr[i];
            }
        }
        return maxPos;
    }
    /**
     * Method to draw a pixel at the specified location on the grid.
     * @param g2 the graphics component to draw with
     * @param x the x coordinate
     * @param y the y coordinate
     * @param pixelColor the color of the pixel
     * @param lineOffsets an array containing the horizontal and vertical offsets of the grid lines
     */
    private void writePixel(Graphics2D g2, int x, int y, Color pixelColor, int[] lineOffsets) {
        int horizontalOffset = lineOffsets[0];
        int verticalOffset = lineOffsets[1];
        int radiusOffset = pixelSize/2;
        g2.setColor(pixelColor);
        g2.drawOval(x * verticalOffset-radiusOffset, y * horizontalOffset-radiusOffset, pixelSize,pixelSize);
    }

    /**
     * Draws the grid lines on the screen.
     *
     * @param g2 the graphics component to draw with
     * @param yLineNum number of gridlines to draw vertically
     * @param xLineNum number of gridlines to draw horizontally
     * @param frameWidth the width of the container to draw the gridlines in
     * @param frameHeight the height of the container to draw the gridlines in
     *
     * @return an array containing the horizontal and vertical offsets of the gridlines, in that order
     */
    private int[] drawGridLines(Graphics2D g2, int yLineNum, int xLineNum, int frameWidth, int frameHeight) {
        int vertLineSpacing = frameHeight / xLineNum;
        int horizLineSpacing = frameWidth / yLineNum;

        if(vertLineSpacing < 2){
            vertLineSpacing = 2;
        }
        if(horizLineSpacing < 2){
            horizLineSpacing = 2;
        }

        for(int i=0; i<xLineNum; i++){
            g2.drawLine(0, i * vertLineSpacing, frameWidth, i * vertLineSpacing);
        }
        for(int i=0; i<yLineNum; i++){
            g2.drawLine(i * horizLineSpacing,0, i * horizLineSpacing, frameHeight);
        }

        return new int[]{vertLineSpacing, horizLineSpacing};
    }
}
