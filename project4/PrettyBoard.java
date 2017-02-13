import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.security.InvalidParameterException;
import java.util.LinkedList;

/**
 * Class to represent a board with nice icons that automatically resize
 *
 * @author Nikil Pancha
 */
public class PrettyBoard extends Board implements ActionListener {

    /**
     * field to store the JFrame holding the board
     */
    private JFrame frame = new JFrame();
    /**
     * field to store the JPanel that holds the board
     */
    private JPanel board;
    /**
     * field to store array of JButtons
     */
    private JButton[][] buttons;
    /**
     * field to store the icon for an empty space
     */
    private Icon emptyIcon;
    /**
     * field to store the icon for possible moves
     */
    private Icon possibilityIcon;
    /**
     * field to store the icon for black's position
     */
    private Icon blackIcon;
    /**
     * field to store the icon for white's position
     */
    private Icon whiteIcon;


    /**
     * Constructor that initializes the board with icons
     *
     * @param height height of board
     * @param width  width of board
     */
    public PrettyBoard(int height, int width) {
        super(height, width);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }
        board = new JPanel(new GridLayout(height, width));
        int squareSize;
        //set size to proportion of the screen size
        int usableHeight = (int) (0.83 * Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        int usableWidth = (int) (0.99 * Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        //if height would be too large, use width
        if (usableHeight * 1.0 / usableWidth < Toolkit.getDefaultToolkit().getScreenSize().getHeight() / Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
            squareSize = usableHeight / height;
        } else {
            squareSize = usableWidth / width;
        }
        initIcons(squareSize);
        initPanelButtons(width, height);
        frame.getContentPane().add(board, "Center");
        frame.setSize(width * squareSize, height * squareSize);
        updatePrettyBoard();

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { //anonymous class to update the window icons and aspect ratio as it is resized
                double desiredAspectRatio = getWidth() * 1.0 / getHeight();
                double currentAspectRatio = frame.getWidth() * 1.0 / frame.getHeight();
                int width = frame.getWidth();
                int height = frame.getHeight();
                if (currentAspectRatio > desiredAspectRatio) {
                    width = (int) (height * desiredAspectRatio);
                } else {
                    height = (int) (width / desiredAspectRatio);
                }
                frame.removeComponentListener(this);
                frame.setSize(width, height);
                int squareSize = frame.getHeight() / getHeight();
                initIcons(squareSize);
                updatePrettyBoard();
                frame.addComponentListener(this);
            }
        });

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Initializes icons as squares to a specified size
     *
     * @param size size of sqares on board
     */
    private void initIcons(int size) {
        emptyIcon = PrettyIcon.getIcon(size, 'g');
        whiteIcon = PrettyIcon.getIcon(size, 'w');
        blackIcon = PrettyIcon.getIcon(size, 'b');
        possibilityIcon = PrettyIcon.getIcon(size, 'p');
    }

    /**
     * Initializes the panel of buttons
     *
     * @param width  width of board to create
     * @param height height of board to create
     */
    private void initPanelButtons(int width, int height) {
        buttons = new JButton[height][width];
        //initialize buttons and add them to JPanel
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                board.add(buttons[i][j]);
            }
        }
    }

    /**
     * Retrieve the icon for an empty space
     *
     * @return icon use in empty space
     */
    public Icon getEmptyIcon() {
        return emptyIcon;
    }

    /**
     * Retrieve the icon used to show possible moves
     *
     * @return icon used to show possible moves
     */
    public Icon getPossibilityIcon() {
        return possibilityIcon;
    }

    /**
     * Retrieve the icon used to show black's position
     *
     * @return icon used to show black's position
     */
    public Icon getBlackIcon() {
        return blackIcon;
    }

    /**
     * Retrieve the icon used to show white's position
     *
     * @return icon used to show white's position
     */
    public Icon getWhiteIcon() {
        return whiteIcon;
    }

    /**
     * Retrieve the frame that holds the main game
     *
     * @return frame containing main game
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Retrieve the array of buttons
     *
     * @return array of buttons on the board
     */
    public JButton[][] getButtons() {
        return buttons;
    }

    /**
     * Update the board to match the current state
     */
    public void updatePrettyBoard() {
        //first, set icons to appropriate colour
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (getPlayer(i, j) < 0) {
                    buttons[i][j].setIcon(getEmptyIcon());
                } else if (getPlayer(i, j) == 0) {
                    buttons[i][j].setIcon(getWhiteIcon());
                } else {
                    buttons[i][j].setIcon(getBlackIcon());
                }
            }
        }
        LinkedList<Move> moveList = possibleMoves(); //list of possible moves to mark
        //set all icons in possible moves to different icon
        for (Move move : moveList) {
            getButton(move.getRow(), move.getColumn()).setIcon(getPossibilityIcon());
        }
        //change title to represent current player
        if (getWhoseTurn() == 1) {
            getFrame().setTitle("Black to move");
        } else {
            getFrame().setTitle("White to move");
        }
    }

    /**
     * Nested class to represent game tiles
     */
    private static class PrettyIcon {
        /**
         * Field to store location of black icon file
         */
        private final static String b = "res/black.png";
        /**
         * Field to store location of white icon file
         */
        private final static String w = "res/white.png";
        /**
         * Field to store location of empty icon file
         */
        private final static String g = "res/green.png";
        /**
         * Field to store location of the icon for possible moves
         */
        private final static String possibilities = "res/possibilities.png";

        /**
         * Method to retrieve icon of specified color and size
         *
         * @param size  size of tile
         * @param color color of tile
         * @return ImageIcon with an image corresponding to the colour input
         * @throws InvalidParameterException if the given colour is not one of those allowed
         */
        private static ImageIcon getIcon(int size, char color) throws InvalidParameterException {
            String interest;
            if (color == 'b') {
                interest = b;
            } else if (color == 'w') {
                interest = w;
            } else if (color == 'g') {
                interest = g;
            } else if (color == 'p') {
                interest = possibilities;
            } else {
                throw new InvalidParameterException("Not a valid colour");
            }
            Image img1 = Toolkit.getDefaultToolkit().getImage(interest);
            return new ImageIcon(img1.getScaledInstance(size, size, Image.SCALE_SMOOTH));

        }

    }

    /**
     * Gets the button at the specified position
     *
     * @param row    row of button
     * @param column column of button
     * @return button in the position of the specified row and column
     */
    public JButton getButton(int row, int column) {
        return buttons[row][column];
    }

}
