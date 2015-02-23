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
    private int verticalLines = 300, horizontalLines = 300;
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

        final JTextField width = new JTextField("200");
        final JTextField height = new JTextField("200");
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
//        int[] x = {50,20,40,20,50,70,60,100,80,100};
//        int[] y = {20,40,60,90,250,220,190,180,120,80};

        // Pacman
        int[] x = {90,60,60,90,120, 90, 120};
        int[] y = {90,130,150,180,150, 130, 120};

        fillPolygon(g2, x, y, Color.yellow, lineOffsets);
        drawPolygon(g2, x, y, Color.lightGray, lineOffsets);

//        int[] x2 = {10, 10, 15, 100, 150, 120,100, 110};
//        int[] y2 = {100, 150, 170, 180, 150, 120, 100, 50};

        // Balloon
        int[] x2 = {50, 75, 85, 75, 40, 60,25, 15, 25};
        int[] y2 = {50, 60, 100, 125, 150, 150,125, 100, 60};

        fillPolygon(g2, x2, y2, Color.blue, lineOffsets);
        drawPolygon(g2, x2, y2, Color.lightGray, lineOffsets);

//        int[] x1 = {220,200,175,150,110,90,80,100,80,100,150,180};
//        int[] y1 = {50,80,120,190,250,230,210,180,120,80,40,50};
//
//        fillPolygon(g2, x1, y1, Color.green, lineOffsets);
//        drawPolygon(g2, x1, y1, Color.black, lineOffsets);
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
    private void drawLine(Graphics2D g2, int x0, int y0, int x1, int y1, Color color,  int[] lineOffsets){
        int w = x1 - x0 ;
        int h = y1 - y0 ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;
        }
        int numerator = longest >> 1 ;
        for (int i=0;i<=longest;i++) {
            writePixel(g2, x0,y0, color, lineOffsets);
            numerator += shortest ;
            if (!(numerator<longest)) {
                numerator -= longest ;
                x0 += dx1 ;
                y0 += dy1 ;
            } else {
                x0 += dx2 ;
                y0 += dy2 ;
            }
        }
    }

    private void drawPolygon(Graphics2D g2, int[] x, int[] y, Color color, int[] lineOffsets) {
        double[][] edges = new double[x.length][4];
        int y0, y1, x0, x1;
        for (int pos = 0; pos <= x.length - 1; pos++) {
            if(pos == x.length - 1){
                x1 = x[0];
                y1 = y[0];
            }else{
                x1 = x[pos + 1];
                y1 = y[pos + 1];
            }
            y0 = y[pos];
            x0 = x[pos];

            drawLine(g2, x0, y0, x1, y1,color, lineOffsets);
        }
    }

    /**
     * Method to get the global edges for a polygon.
     * @param x
     * @param y
     * @return
     */
    private double[][] getEdges(int[] x, int[] y) {
        double[][] edges = new double[x.length][4];
        int y0, y1, x0, x1;
        for (int pos = 0; pos <= x.length - 1; pos++) {
            if (pos == x.length - 1) {
                y1 = y[0];
                x1 = x[0];
            }else{
                y1 = y[pos + 1];
                x1 = x[pos + 1];
            }
            y0 = y[pos];
            x0 = x[pos];

            // If y0 greater than y1 return y1 position else return y0 position
            int minYPos;
            if(pos == x.length - 1)
                minYPos = y0 > y1 ? 0 : pos;
            else
                minYPos = y0 >= y1 ? pos + 1 : pos;
            int maxY = y0 > y1 ? y0 : y1;
            edges[pos][0] = y[minYPos];
            edges[pos][1] = maxY;
            edges[pos][2] = x[minYPos];
            double dy = (double) y1 - (double) y0;
            double dx = (double) x1 - (double) x0;
            if (Math.abs(dx) > 0)
                edges[pos][3] = dx / dy;
            else
                edges[pos][3] = 0;
        }


        int j;
        int lengthOfGlobalEdges = 0;
        int[] posForGlobal = new int[edges.length];
        int k = 0;
        for (int i = 0; i < edges.length; i++) {
            if(!Double.isInfinite(edges[i][3])) {
                posForGlobal[k] = i;
                k++;
                lengthOfGlobalEdges++;
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
        globalEdges = sortEdges(globalEdges);
        return globalEdges;
    }

    /**
     * Method to fill a polygon with color.
     * @param g2 the graphics
     * @param x array of x coordinates
     * @param y array of y coordinates
     * @param color color to fill shape with
     * @param lineOffsets vertical and horizontal offset of the pixels
     */
    private void fillPolygon(Graphics2D g2, int[] x, int[] y, Color color, int[] lineOffsets) {
        double[][] globalEdges = getEdges(x, y);
        int scanLine = (int)globalEdges[0][0];
        int activeEdgeSize = 0;
        while (scanLine < y[maxPosition(y)]) {
            double[][] activeEdges = getActiveEdgeList(globalEdges, scanLine);
            int parity = 0;
            int activePosition = 0;
            int yPos = scanLine;
            int xPos = 0;
            while(xPos < activeEdges[getMaxXPos(activeEdges)][2]) {
                if (parity % 2 != 0) {
                    writePixel(g2, xPos, yPos, color, lineOffsets);
                    g2.setColor(color.black);
                    g2.drawString(parity + "", xPos * lineOffsets[1] + 2, yPos * lineOffsets[0] + 2);
                }
                if ((int)activeEdges[activePosition][2] == xPos)  {
                    parity = (parity + 1) % 2;
                    activePosition++;
                    activeEdges = getActiveEdgeList(globalEdges, scanLine);
                    g2.setColor(color.black);
                    g2.setFont(new Font(Font.DIALOG, Font.PLAIN, 6));
                    g2.drawString(parity + "", xPos * lineOffsets[1] + 2, yPos * lineOffsets[0] + 2);
                }
                xPos++;
            }
            scanLine++;
            activeEdges = getActiveEdgeList(globalEdges, scanLine);
            activeEdges = updateActiveEdges(activeEdges);
            activePosition = 0;
            parity = 0;
        }

    }

    private double[][] getActiveEdgeList(double[][] globalEdges, int scanLine){
        int activeEdgeSize = 0;
        for (int i = 0; i < globalEdges.length; i++) {
            if (globalEdges[i][0] == scanLine && globalEdges[i][1] >= scanLine) {
                activeEdgeSize++;
            }
            if(globalEdges[i][0] < scanLine && globalEdges[i][1] > scanLine){
                activeEdgeSize++;
            }
        }
        double[][] activeEdges = new double[activeEdgeSize][4];
        for (int i = 0; i < activeEdgeSize; i++) {
            for(int j = i; j < globalEdges.length ; j++) {
                if (globalEdges[j][0] == scanLine && globalEdges[j][1] >= scanLine) {
                    activeEdges[i] = globalEdges[j];
                    i++;
                }
                if (globalEdges[j][0] < scanLine && globalEdges[j][1] > scanLine) {
                    activeEdges[i] = globalEdges[j];
                    i++;
                }
            }
        }
        activeEdges = sortEdges(activeEdges);
        return activeEdges;
    }

    private int getMaxXPos(double[][] arr) {
        int maxPos = 0;
        for (int i = 1; i < arr.length; i++) {
            if(arr[maxPos][2] < arr[i][2]){
                maxPos = i;
            }
        }
        return maxPos;
    }

    private int getMaxYPos(double[][] arr) {
        int maxPos = 0;
        for (int i = 1; i < arr.length; i++) {
            if(arr[maxPos][1] < arr[i][1]){
                maxPos = i;
            }
        }
        return maxPos;
    }

    private double[][] updateActiveEdges(double[][] arr) {
        double[][] updatedArr = new double[arr.length][4];
        for (int i = 0; i < arr.length; i++) {
            updatedArr[i] = arr[i];
            updatedArr[i][2] += updatedArr[i][3];
        }
        return sortEdges(updatedArr);
    }

    private double[][] sortEdges(double[][] arr) {
        double key;
        int j;
        for (int i = 0; i < arr.length; i ++){
            key = arr[i][1];
            j = i - 1;
            while (j >= 0 && arr[j][1] >= key) {
                double[] tmp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = tmp;
                j = j - 1;
            }
            arr[j+1][1] = key;
        }
        for (int i = 0; i < arr.length; i ++){
            key = arr[i][2];
            j = i - 1;
            while (j >= 0 && arr[j][2] <= key) {
                double[] tmp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = tmp;
                j = j - 1;
            }
            arr[j+1][2] = key;
        }
        for (int i = 0; i < arr.length; i ++){
            key = arr[i][0];
            j = i - 1;
            while (j >= 0 && arr[j][0] >= key){
                double[] tmp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = tmp;
                j = j-1;
            }
            arr[j+1][0] = key;
        }
        return arr;
    }

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
