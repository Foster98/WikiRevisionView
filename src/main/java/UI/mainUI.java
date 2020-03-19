package UI;

import Parse.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import Domain.*;

import Edit.*;
import Revise.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class mainUI extends JFrame implements ActionListener {
    static JTextArea revisionList;
    static JTextArea editorsText;
    static JTextArea redirected;
    static JFrame frame;
    static JTextField text;
    static JButton button;
    static JLabel noConnection;
    static JLabel noPage;
    static JLabel label;

    public static void main(String[] args) {
        new mainUI();


    }

    public mainUI() {

        super("Wikipedia Revisions UI");

        UIManager.put("Label.font", new FontUIResource(new Font("Tacoma", Font.BOLD, 15)));

        UIManager.put("Button.font", new FontUIResource(new Font("Verdana", Font.PLAIN, 30)));

        frame = new JFrame("Edits:");
        label = new JLabel("Enter article name: ");
        button = new JButton("Search!");

        text = new JTextField(6);
        revisionList = new JTextArea();

        editorsText = new JTextArea();
        noPage = new JLabel();

        redirected = new JTextArea();

        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel panel = new JPanel();

        panel.setLayout(gridBagLayout);
        panel.add(button);
        panel.add(text);
        panel.add(label);



        setContentPane(panel);

        button.addActionListener(e -> {
            String article = text.getText();
            try {
                WikiPage page = new WikiPage();
                InputStream connection = page.wikiConnectionBuilder(article);

                //checks connection
                if (connection == null) {
                    noConnection.setText("Connection Error...");
                    var noConnectionConstraints = new GridBagConstraints(1,1,1,1,1,1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, new Insets(1,1,1,1), 0,0);
                    panel.add(noConnection,noConnectionConstraints);
                }


                parser parser = new parser();
                JsonObject query = parser.parseData(connection);

                if (query == null) {
                    noPage.setText("no page exists");
                    var noPageConstraints = new GridBagConstraints(0,4,1,1,1,1, GridBagConstraints.ABOVE_BASELINE, GridBagConstraints.NORTHWEST, new Insets(1,1,1,1),0,0);
                    panel.add(noPage, noPageConstraints);
                } else {


                    RevisionObjGetter getter = new RevisionObjGetter();
                    JsonArray revisions = getter.getRevisionsObject(query);

                    // Check ze redirects etc
                    checkRedirect checker = new checkRedirect();
                    String redirects = checker.checkForRedirects(query);
                    if (redirects != null) {
                        panel.remove(button);
                        panel.remove(text);
                        panel.remove(label);


                        redirected.setText("You have been redirected from " + article  + '\n' + " to " + redirects);


                        panel.add(redirected);

                        redirected.setPreferredSize(new Dimension(300,700));
                        redirected.updateUI();


                        panel.updateUI();

                        redirected.setColumns(8);
                        redirected.getPreferredSize();








                    }

                    RevisionList revisionLister = new RevisionList();
                    revisionList.setText(revisionLister.listRevisions(revisions));
                    revisionList.setPreferredSize(new Dimension(500,500));
                    revisionList.setColumns(4);
                    revisionList.setLocation(200, 0);

                    panel.add(revisionList);



                    EditSort editorSort = new EditSort();
                    String result = editorSort.sortEdits(revisions).toString();

                    editorsText.setText(result);
                    editorsText.setPreferredSize(new Dimension(600,500));
                    panel.add(editorsText);
                    editorsText.setColumns(4);
                    editorsText.setLocation(1000, 0);


                }
                panel.updateUI();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            panel.remove(button);
            panel.remove(text);
            panel.remove(label);
            panel.updateUI();

        });
        setPreferredSize(new Dimension(1500,1500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);


    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {






    }
}