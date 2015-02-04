import javax.swing.*;
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
    public static void main(String[] args){
        new Screen();
    }

    public Screen(){
        JFrame contentFrame = new JFrame("Screen Simulator");
        GroupLayout layout = new GroupLayout(contentFrame.getContentPane());
        JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        final JTextField width = new JTextField("width");
        final JTextField height = new JTextField("height");
        JButton resize = new JButton("Resize");
        resize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateSize(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
            }
        });
        contentFrame.setMinimumSize(new Dimension(800, 600));
        contentFrame.setLocation(0, 0);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()).addComponent(width).addComponent(height)
                .addGroup(layout.createParallelGroup()).addComponent(resize)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup()).addComponent(width).addComponent(height)
                .addGroup(layout.createSequentialGroup()).addComponent(resize)
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
        drawLine(g2,0,0,50,300,lineOffsets);
        drawLine(g2,0,10,50,10,lineOffsets);
        drawLine(g2, 10, 50, 300, 80, lineOffsets);
        drawLine(g2, 30, 10, 505,300, lineOffsets);
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
        int dx = x1 - x0, dy=y1-y0;
        int d=2*dy-dx;
        int incrE=2*dy, incrNE=2*(dy-dx);
        int x=x0, y=y0;

        writePixel(g2, x, y, Color.red, lineOffsets);
        do {
            writePixel(g2, x, y, Color.red, lineOffsets);
            if (d <= 0) {
                x++;
                d += incrE;
            } else {
                x++;
                y++;
                d += incrNE;
            }
        } while (x <= x1);

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
        g2.setColor(pixelColor);
        g2.drawOval(x * verticalOffset-2, y * horizontalOffset-2, 5,5);
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
