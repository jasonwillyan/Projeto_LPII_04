package projeto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu.Separator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

public class Editor extends JFrame implements ActionListener {

    private JTextArea areaDeTexto = new JTextArea();
    private JScrollPane scroll = new JScrollPane();
    private JMenuBar menuBar = new JMenuBar();
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JFileChooser fileChooser = new JFileChooser();
    private BarraEstado bEstado = new BarraEstado();

    public Editor() {
        configuraJanela();
        scroll.add(areaDeTexto);
        scroll.setViewportView(areaDeTexto);
        this.add(scroll);
        this.add(bEstado.panelJ, BorderLayout.SOUTH);
        this.setVisible(true);
        // scroll.setLayout(new BorderLayout());
        areaDeTexto.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int linenum = 1;
                int columnnum = 1;
                int caretpos = areaDeTexto.getCaretPosition();
                try {
                    linenum = areaDeTexto.getLineOfOffset(caretpos);
                    columnnum = caretpos - areaDeTexto.getLineStartOffset(linenum);
                } catch (BadLocationException ex) {
                }
                bEstado.linhaCol(columnnum + 1, linenum + 1);
            }
        });

    }

    public class BarraEstado {
        public JPanel panelJ = new JPanel();
        public JLabel labelLeft = new JLabel();
        public JLabel labelRight = new JLabel();
        public String name;
        public int x, y;

        public BarraEstado() {
            super();
            panelJ.setLayout(new GridLayout(1, 2));
            panelJ.add(labelLeft, BorderLayout.WEST);
            panelJ.add(labelRight, BorderLayout.EAST);
            labelLeft.setText("Estado");
            labelRight.setHorizontalAlignment(SwingConstants.RIGHT);
        }

        public void linhaCol(int x, int y) {
            this.x = x;
            this.y = y;
            nameLabel();
        }

        public void nameArquivo(String name) {
            this.name = name;
            nameLabel();
        }

        private void nameLabel() {
            labelLeft.setText("Caminho: " + name);
            labelRight.setText(" Linha: " + y + " Coluna: " + x + "        ");
        }

    }

    private void configuraJanela() {
        this.setSize(400, 400);
        this.setTitle("EDITOR DE TEXTO EM JAVA");
        // this.setLayout(new GridLayout(0, 2));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Top");
        DefaultMutableTreeNode A = new DefaultMutableTreeNode("A");
        DefaultMutableTreeNode B = new DefaultMutableTreeNode("B");
        DefaultMutableTreeNode A1 = new DefaultMutableTreeNode("A1");
        DefaultMutableTreeNode A2 = new DefaultMutableTreeNode("A2");
        DefaultMutableTreeNode A3 = new DefaultMutableTreeNode("A3");
        DefaultMutableTreeNode B1 = new DefaultMutableTreeNode("B1");
        DefaultMutableTreeNode B2 = new DefaultMutableTreeNode("B2");
        DefaultMutableTreeNode B3 = new DefaultMutableTreeNode("B3");

        A.add(A1);
        A.add(A2);
        A.add(A3);
        B.add(B1);
        B.add(B2);
        B.add(B3);
        rootNode.add(A);
        rootNode.add(B);

        JTree tree = new JTree(rootNode);
        this.add(tree, BorderLayout.WEST);

        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Arquivo");
        menuBar.add(fileMenu);
        // JMenuItem actionNew = new JMenuItem("Novo");
        // actionNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
        // KeyEvent.CTRL_MASK));
        JMenuItem actionOpen = new JMenuItem("Abrir");
        actionOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        JMenuItem actionClose = new JMenuItem("Fechar");
        actionClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        JMenuItem actionSave = new JMenuItem("Salvar");
        actionSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        JMenuItem actionSaveAs = new JMenuItem("Salvar como");
        // actionNew.addActionListener(this);
        actionSaveAs.addActionListener(this);
        actionOpen.addActionListener(this);
        actionClose.addActionListener(this);
        actionSave.addActionListener(this);
        // fileMenu.add(actionNew);
        // fileMenu.addSeparator();
        fileMenu.add(actionOpen);
        fileMenu.addSeparator();
        fileMenu.add(actionSave);
        fileMenu.addSeparator();
        fileMenu.add(actionSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(actionClose);
    }

    public void save() {
        if (fileChooser.getSelectedFile() == null) {
            int result = fileChooser.showDialog(this, "Salvar");
            if (result == JFileChooser.CANCEL_OPTION) {
                return;
            }
        }
        File arquivo = fileChooser.getSelectedFile();
        try {
            arquivo.createNewFile();
            PrintStream stream = new PrintStream(arquivo);
            stream.print(areaDeTexto.getText());
            stream.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro durante a gravação do arquivo '"
                    + fileChooser.getSelectedFile().getAbsolutePath() + "'");
        }
        bEstado.nameArquivo(fileChooser.getSelectedFile().getAbsolutePath());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Salvar como")) {
            int var = fileChooser.showDialog(this, "Salvar como");
            if (var == JFileChooser.CANCEL_OPTION) {
                return;
            }
            save();
        } else if (e.getActionCommand().equals("Abrir")) {
            int var = fileChooser.showOpenDialog(this);
            if (var == JFileChooser.CANCEL_OPTION) {
                return;
            }
            try {
                FileReader ler = new FileReader(fileChooser.getSelectedFile().getAbsolutePath());
                BufferedReader bReader = new BufferedReader(ler);
                areaDeTexto.read(bReader, null);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this,
                        "Arquivo não encontrado '" + fileChooser.getSelectedFile().getAbsolutePath() + "'");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro durante a leitura do arquivo '"
                        + fileChooser.getSelectedFile().getAbsolutePath() + "'");
            }
            bEstado.nameArquivo(fileChooser.getSelectedFile().getAbsolutePath());
        } else if (e.getActionCommand().equals("Fechar")) {
            this.setVisible(false);
            this.dispose();
        } else if (e.getActionCommand().equals("Salvar")) {
            save();
        }
    }

    public static void main(String[] args) {
        new Editor();
    }
}
