import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SandpileModel extends JFrame {

    private JLabel[][] gridLabels;
    private int[][] gridValues;
    private int N = 13;

    public SandpileModel() {
        
        super("Sandpile Model");

        
        gridValues = new int[N][N];
        gridLabels = new JLabel[N][N];

        
        setLayout(new BorderLayout());

        
        JLabel titleLabel = new JLabel("Sandpile Model", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        
        JPanel gridPanel = new JPanel(new GridLayout(13, 13));
        initializeGrid();
        initializeGridLabels();
        updateGridLabels();

        Border margin = BorderFactory.createLineBorder(Color.BLACK, 1);
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gridLabels[i][j].setBorder(margin);
                gridPanel.add(gridLabels[i][j]);
            }
        }

        
        add(gridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonClicked();
            }
        });
        bottomPanel.add(playButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }


    private void initializeGrid() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gridValues[i][j] = 0; //(int) (Math.random() * 4);
            }
        }
    }


    private void initializeGridLabels() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gridLabels[i][j] = new JLabel();
                gridLabels[i][j].setHorizontalAlignment(JLabel.CENTER);
                gridLabels[i][j].setVerticalAlignment(JLabel.CENTER);
                updateLabelState(i, j);
            }
        }
    }

    private void updateGridLabels() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                updateLabelState(i, j);
            }
        }
        revalidate();
    }


    private void updateLabelState(int row, int col) {
        int value = gridValues[row][col];
        gridLabels[row][col].setText(String.valueOf(value));
        Color color;
        switch (value) {
            case 0:
                color = Color.BLUE;
                break;
            case 1:
                color = Color.GREEN;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.ORANGE;
                break;
            default:
                color = Color.BLACK;
        }
        gridLabels[row][col].setBackground(color);
        gridLabels[row][col].setOpaque(true);
    }

    private void addOne(int row, int col){
        gridValues[row][col] += 1;
        if(gridValues[row][col] >= 4){
            gridValues[row][col] = 0;
            if(row + 1 < N)
                addOne(row + 1, col);
            if(row - 1 >= 0)
                addOne(row - 1, col);
            if(col + 1 < N)
                addOne(row, col + 1);
            if(col - 1 >= 0)
                addOne(row, col - 1);
        }
    }

    private void playButtonClicked() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    addOne(6, 6);
                    updateGridLabels();
                    /*
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            System.out.print(gridValues[i][j] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();*/
                    repaint();
                });
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SandpileModel());
    }
}

