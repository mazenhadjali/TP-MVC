package view;

import controller.PersonController;
import controller.PersonControllerImpl;
import model.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BaseV extends JFrame {

    private static BaseV instance;

    PersonControllerImpl personController = new PersonControllerImpl();

    private JLabel idL = new JLabel("Id");
    private JLabel nomL = new JLabel("Nom");
    private JTextField idT = new JTextField();
    private JTextField nomT = new JTextField();

    private JButton ajouterBtn = new JButton("Ajouter");
    private JButton supprimerBtn = new JButton("Supprimer");
    private JButton modifierBtn = new JButton("Modifier");
    private JButton consulterBtn = new JButton("Consulter");

    private String entete[] = {"Id", "Nom"};
    private DefaultTableModel model = new DefaultTableModel(entete, 0);
    private JTable tab = new JTable(model);
    private JScrollPane sp = new JScrollPane(tab);

    private BaseV() throws Exception {
        setTitle("Base");
        setSize(500, 300);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel pNorth = new JPanel(new GridLayout(2, 2));
        pNorth.add(idL);
        pNorth.add(idT);
        pNorth.add(nomL);
        pNorth.add(nomT);

        JPanel pSouth = new JPanel(new GridLayout(1, 4));
        pSouth.add(ajouterBtn);
        pSouth.add(supprimerBtn);
        pSouth.add(modifierBtn);
        pSouth.add(consulterBtn);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add("North", pNorth);
        c.add("Center", sp);
        c.add("South", pSouth);

        // Add controller calls
        populateTable();

        // Add listener for row selection
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tab.getSelectedRow();
                if (row != -1) {
                    // If a row is clicked, populate the fields with the selected person's data
                    idT.setText(String.valueOf(tab.getValueAt(row, 0)));
                    nomT.setText(String.valueOf(tab.getValueAt(row, 1)));
                }
            }
        });

        addListeners();

        setVisible(true);
    }

    private void populateTable() {
        model.setRowCount(0);

        for (Person person : personController.getAllPersons()) {
            String[] rowData = {String.valueOf(person.getId()), person.getNom()};
            model.addRow(rowData);
        }
    }

    private void addPerson() {
        String nom = nomT.getText();
        Person newPerson = new Person(0, nom); // Assuming 0 as a default id, you may adjust accordingly
        Person createdPerson = personController.createPerson(newPerson);
        if (createdPerson != null) {
            JOptionPane.showMessageDialog(this, "Person added successfully");
            populateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add person");
        }
    }

    private void updatePerson() {
        int id = Integer.parseInt(idT.getText());
        String nom = nomT.getText();
        Person updatedPerson = new Person(id, nom);
        Person result = personController.updatePerson(updatedPerson);
        if (result != null) {
            JOptionPane.showMessageDialog(this, "Person updated successfully");
            populateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update person");
        }
    }

    private void deletePerson() {
        int id = Integer.parseInt(idT.getText());
        boolean deleted = personController.deletePerson(id);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Person deleted successfully");
            populateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete person");
        }
    }

    private void viewPerson() {
        int id = Integer.parseInt(idT.getText());
        Person person = personController.getPersonById(id);
        if (person != null) {
            JOptionPane.showMessageDialog(this, "Person Details:\nID: " + person.getId() + "\nNom: " + person.getNom());
        } else {
            JOptionPane.showMessageDialog(this, "Person not found");
        }
    }

    private void addListeners() {
        ajouterBtn.addActionListener(e -> addPerson());
        modifierBtn.addActionListener(e -> updatePerson());
        supprimerBtn.addActionListener(e -> deletePerson());
        consulterBtn.addActionListener(e -> viewPerson());
    }


    public static BaseV getInstance() throws Exception {
        if (instance == null) {
            instance = new BaseV();
        }
        return instance;
    }
}
