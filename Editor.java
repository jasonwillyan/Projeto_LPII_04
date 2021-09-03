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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Editor extends JFrame implements ActionListener {

    private JTextPane areaDeTexto = new JTextPane();
    private JScrollPane scroll = new JScrollPane();
    private JMenuBar menuBar = new JMenuBar();
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;

    public Editor() {

        configuraJanela();
        scroll.add(areaDeTexto);
        scroll.setViewportView(areaDeTexto);
        this.add(scroll, BorderLayout.EAST);
        this.setVisible(true);
    }

    private void configuraJanela() {
        this.setSize(400, 400);
        this.setTitle("EDITOR DE TEXTO EM JAVA");
        this.setLayout(new GridLayout(0, 2));
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
        JMenuItem newAction = new JMenuItem("Novo");
        fileMenu.add(newAction);
        newAction.addActionListener(this);
        JMenuItem newAction2 = new JMenuItem("Abrir");
        fileMenu.add(newAction2);
        JMenuItem newAction3 = new JMenuItem("Fechar");
        fileMenu.add(newAction3);
        JMenuItem newAction4 = new JMenuItem("Salvar");
        fileMenu.add(newAction4);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Novo")) {
            System.out.println(" ");
        } else if (e.getActionCommand().equals("Abrir")) {
            System.out.println(" ");
        } else if (e.getActionCommand().equals("Fechar")) {
            System.out.println(" ");
        } else if (e.getActionCommand().equals("Salvar")) {
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {
        new Editor();
    }
}
