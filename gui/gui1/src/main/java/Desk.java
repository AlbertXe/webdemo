import javax.swing.*;

public class Desk {
    private JPanel panel1;
    private JTextField textField1;
    private JButton button;
    private JLabel labelpre;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Desk");
        frame.setContentPane(new Desk().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Desk() {
        button.addActionListener((t) -> {

        });
    }
}
