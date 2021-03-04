/*
 * Class Section: TT6V & TC1V
 * Trimester 2 2020/21
 * Chan Lin Chee
 * 1191202546
 * 016 357 7219
 */


// import necessary packages for needed classes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaIndividualProject extends JFrame implements ActionListener {

    // initialize global variables (shared across all instances)
    private static JLabel intersectLabel = new JLabel("Two circles intersect?  " + "NO", JLabel.CENTER);
    private static JLabel coordinate = new JLabel("", JLabel.CENTER);
    private static int circleMode = 0;          // used for check the cursor pressed in which circle
    private static int colorOption = 0;         // used for check the color option chosen by user

    // initialize circle 1's center coordinate & radius
    // set as global variables (shared across all instances)
    private static int centerX1 = 50;
    private static int centerY1 = 50;
    private static int radius1 = 50;

    // initialize circle 2's center coordinate & radius
    // set as global variables (shared across all instances)
    private static int centerX2 = 160;
    private static int centerY2 = 50;
    private static int radius2 = 50;

    // display circle 1's center coordinate & radius in particular text fields
    // set as global variables (shared across all instances)
    private static JTextField centerX1_TF = new JTextField(String.valueOf(centerX1));
    private static JTextField centerY1_TF = new JTextField(String.valueOf(centerY1));
    private static JTextField radius1_TF = new JTextField(String.valueOf(radius1));

    // display circle 2's center coordinate & radius in particular text fields
    // set as global variables (shared across all instances)
    private static JTextField centerX2_TF = new JTextField(String.valueOf(centerX2));
    private static JTextField centerY2_TF = new JTextField(String.valueOf(centerY2));
    private static JTextField radius2_TF = new JTextField(String.valueOf(radius2));

    // create an object for each class
    private final displayCircles drawC = new displayCircles();      // for draw the circles
    private final draggingCircles moveC = new draggingCircles();    // for receive mouse event

    // declare GUI components
    private final JButton redrawBtn;
    private JLabel cXLabel1, cYLabel1, rLabel1, cXLabel2, cYLabel2, rLabel2;

    public static void main (String [] args) {
        // set up the frame
        JavaIndividualProject frame = new JavaIndividualProject();
        frame.setSize(400,480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("1191202546 Chan Lin Chee");
    }

    // constructor without parameter (will be invoked when the object of JavaIndividualProject has been created)
    public JavaIndividualProject() {

        // set the layout of the whole JFrame
        setLayout(new BorderLayout());

        // create an object array to include the choices (used for Option Dialog)
        Object[] option = { "Black for Both", "C1: Red || C2: Blue", "C1: Blue || C2: Red"};

        // create an Option Dialog for color selection of circles (return an integer)
        colorOption = JOptionPane.showOptionDialog(null, "Please choose the color for both circles",
                "Color Selection", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, option, null);

        // north of the whole JFrame
        JPanel dataPanel = new JPanel(new GridLayout(2,1));
            intersectLabel.setFont(new Font("Calibri",Font.BOLD,18));
            dataPanel.add(intersectLabel);      // display the intersecting condition of both circles
            dataPanel.add(coordinate);          // display the coordinate of cursor where the mouse button has been pressed

        // south of the whole JFrame
        JPanel inputContainer = new JPanel(new BorderLayout());
            // panel as a container for two 'boxes'
            JPanel container = new JPanel();

                // 'box' for display circle 1's coordinate and radius info
                JPanel containerC1 = new JPanel(new BorderLayout());
                containerC1.setBorder(BorderFactory.createLineBorder(Color.black));
                    JLabel info1 = new JLabel("Enter circle 1 info : ",JLabel.LEFT);
                    JPanel C1body = new JPanel(new GridLayout(3,2));
                        C1body.add(cXLabel1 = new JLabel("Center x : "));
                        C1body.add(centerX1_TF);
                        C1body.add(cYLabel1 = new JLabel("Center y : "));
                        C1body.add(centerY1_TF);
                        C1body.add(rLabel1 = new JLabel("Radius : "));
                        C1body.add(radius1_TF);
                containerC1.add(info1, BorderLayout.NORTH);
                containerC1.add(C1body, BorderLayout.CENTER);

                // 'box' for display circle 2's coordinate and radius info
                JPanel containerC2 = new JPanel(new BorderLayout());
                containerC2.setBorder(BorderFactory.createLineBorder(Color.black));
                    JLabel info2 = new JLabel("Enter circle 2 info : ");
                    JPanel C2body = new JPanel(new GridLayout(3,2));
                        C2body.add(cXLabel2 = new JLabel("Center x : "));
                        C2body.add(centerX2_TF);
                        C2body.add(cYLabel2 = new JLabel("Center y : "));
                        C2body.add(centerY2_TF);
                        C2body.add(rLabel2 = new JLabel("Radius : "));
                        C2body.add(radius2_TF);
                containerC2.add(info2, BorderLayout.NORTH);
                containerC2.add(C2body, BorderLayout.CENTER);

            container.add(containerC1);
            container.add(containerC2);

            // south of the inputContainer (contain button)
            JPanel buttonPanel = new JPanel();
                buttonPanel.add(redrawBtn = new JButton("Redraw Circles"));

        inputContainer.add(container, BorderLayout.CENTER);
        inputContainer.add(buttonPanel, BorderLayout.SOUTH);

        // transparent border for setting the margin
        info1.setBorder(new EmptyBorder(5,5,5,5));
        info2.setBorder(new EmptyBorder(5,5,5,5));
        C1body.setBorder(new EmptyBorder(5,5,5,5));
        C2body.setBorder(new EmptyBorder(5,5,5,5));

        add(dataPanel, BorderLayout.NORTH);             // added to frame
        add(drawC, BorderLayout.CENTER);                // added to frame
        add(inputContainer, BorderLayout.SOUTH);        // added to frame

        redrawBtn.addActionListener(this);          // for handling action event (redraw circles)
        drawC.addMouseListener(moveC);                // for handling mouse event which not in motion (such as mouse pressed)
        drawC.addMouseMotionListener(moveC);          // for handling mouse motion event (such as mouse dragged)
    }

    // handle the action of components
    public void actionPerformed(ActionEvent e) {

        // if an event occurred on the redrawBtn
        if(e.getSource() == redrawBtn) {

            // exception handling
            try{
                // get Circle 1's info from user input
                centerX1 = Integer.parseInt(centerX1_TF.getText());
                centerY1 = Integer.parseInt(centerY1_TF.getText());
                radius1 = Integer.parseInt(radius1_TF.getText());

                // get Circle 2's info from user input
                centerX2 = Integer.parseInt(centerX2_TF.getText());
                centerY2 = Integer.parseInt(centerY2_TF.getText());
                radius2 = Integer.parseInt(radius2_TF.getText());

                // check if the circles' info are not suitable
                if(centerX1<0 || centerY1<0 || radius1<=0 || centerX2<0 || centerY2<0 || radius2<=0) {
                    // prompt user to insert positive integer
                    JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer number in every text field","Invalid Input",
                            JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("negative.jpg")));
                }
                else {
                    drawC.repaint();        // redraw the circles based on user input
                    moveC.findIntersectionCondition();      // look for the intersecting condition of both circles
                }
            }
            // provide catch block for handle NumberFormatException (such as user input is empty or is not a number)
            catch(NumberFormatException err) {
                // prompt user to insert legal integer
                JOptionPane.showMessageDialog(getContentPane(), "Please enter a legal integer number in every text field","Number Format Exception",
                        JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("invalid.jpg")));
            }
        }
    }

    // used for draw circles on the center panel
    class displayCircles extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // draw circles based on the colors chosen by user
            if(colorOption == 0) {          // black for both
                g.setColor(Color.black);
                g.drawOval((centerX1-radius1),(centerY1-radius1),(radius1*2),(radius1*2));
                g.drawOval((centerX2-radius2),(centerY2-radius2),(radius2*2),(radius2*2));
            }
            else if(colorOption == 1) {     // Red for circle 1, Blue for circle 2
                g.setColor(Color.red);
                g.drawOval((centerX1-radius1),(centerY1-radius1),(radius1*2),(radius1*2));
                g.setColor(Color.blue);
                g.drawOval((centerX2-radius2),(centerY2-radius2),(radius2*2),(radius2*2));
            }
            else if(colorOption == 2) {     // Blue for circle 1, Red for circle 2
                g.setColor(Color.blue);
                g.drawOval((centerX1-radius1),(centerY1-radius1),(radius1*2),(radius1*2));
                g.setColor(Color.red);
                g.drawOval((centerX2-radius2),(centerY2-radius2),(radius2*2),(radius2*2));
            }
        }
    }

    // used for receive mouse events
    class draggingCircles extends MouseAdapter {

        //  invoked when the mouse button has been pressed on the center panel
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            int cursorX = e.getX();     // get the coordinate x of the cursor when the mouse button has been pressed
            int cursorY = e.getY();     // get the coordinate y of the cursor when the mouse button has been pressed

            circleMode = 0;     // re-initialize the circle mode as it is not pressed inside circle 1 nor circle 2

            // display the coordinate of cursor where the mouse button has been pressed
            coordinate.setText("Pressed at :  X:"+String.valueOf(cursorX)+"   Y:"+String.valueOf(cursorY));

            // calculate the starting coordinate for draw the circle 1
            int rOneX = centerX1 - radius1;        int rOneY = centerY1 - radius1;
            // calculate the starting coordinate for draw the circle 2
            int rTwoX = centerX2 - radius2;        int rTwoY = centerY2 - radius2;

            // check if the cursor is pressed inside the area of circle 1
            if(cursorX>=rOneX && cursorX<=(rOneX+(2*radius1))
                    && cursorY>=rOneY && cursorY<=(rOneY+(2*radius1))) {

                double distance = Math.sqrt( Math.pow((cursorX - centerX1),2) + Math.pow((cursorY-centerY1),2) );

                if(distance<=radius1) {
                    circleMode = 1;
                }
            }
            // check if the cursor is pressed inside the area of circle 2
            else if(cursorX>=rTwoX && cursorX<=(rTwoX+(2*radius2))
                    && cursorY>=rTwoY && cursorY<=(rTwoY+(2*radius2))) {

                double distance = Math.sqrt( Math.pow((cursorX - centerX2),2) + Math.pow((cursorY-centerY2),2) );

                if(distance<=radius2) {
                    circleMode = 2;
                }
            }
        }

        // invoked when the mouse button is pressed on the center panel and then dragged
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);

            int cursorX = e.getX();     // get the coordinate x of the cursor when the mouse button has been pressed
            int cursorY = e.getY();     // get the coordinate y of the cursor when the mouse button has been pressed

            // if the cursor is pressed inside circle 1
            if(circleMode == 1) {
                // set the cursor to be the center of circle 1
                centerX1 = cursorX;
                centerY1 = cursorY;

                centerX1_TF.setText(String.valueOf(centerX1));      // display the coordinate x of circle 1 when it is being dragged
                centerY1_TF.setText(String.valueOf(centerY1));      // display the coordinate y of circle 1 when it is being dragged
            }

            // if the cursor is pressed inside circle 2
            if(circleMode == 2) {
                // set the cursor to be the center of circle 2
                centerX2 = cursorX;
                centerY2 = cursorY;

                centerX2_TF.setText(String.valueOf(centerX2));      // display the coordinate x of circle 2 when it is being dragged
                centerY2_TF.setText(String.valueOf(centerY2));      // display the coordinate y of circle 2 when it is being dragged
            }

            drawC.repaint();        // redraw the center panel of the whole JFrame (drawC)
            findIntersectionCondition();    // invoke the method to look for the intersecting condition of both circles
        }

        // will be invoked for finding the intersecting condition of both circles
        public void findIntersectionCondition ( ) {
            // use mathematical concepts to find the intersecting condition of two circles: based on radius and distance

            double distance = Math.sqrt( Math.pow((centerX2 - centerX1),2) + Math.pow((centerY2-centerY1),2) );

            if(radius1==radius2 && distance == 0) {
                intersectLabel.setText("Two circles intersect?  " + "Yes, completely overlap");
            }
            else if((distance+radius2)<radius1) {
                intersectLabel.setText("Two circles intersect?  " + "Yes, Circle 2 is in Circle 1");
            }
            else if((distance+radius1)<radius2) {
                intersectLabel.setText("Two circles intersect?  " + "Yes, Circle 1 is in Circle 2");
            }
            else if(distance<=(radius1+radius2)){
                intersectLabel.setText("Two circles intersect?  " + "YES");
            }
            else
                intersectLabel.setText("Two circles intersect?  " + "NO");
        }
    }
}
