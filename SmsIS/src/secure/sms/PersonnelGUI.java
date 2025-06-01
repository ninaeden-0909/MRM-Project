/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package secure.sms;
import javax.swing.JOptionPane;
import all_requests.Request;
import all_requests.RequestDAOImpl;
import consumables.Consumable;
import consumables.ConsumableDAOImpl;
import equipments.Equipment;
import equipments.EquipmentDAOImpl;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import others.Others;
import others.OthersDAOImpl;
import responses.Response;
import responses.ResponseDAO;
import responses.ResponseDAOImpl;
import session.LoggedInUser;
import technologies.Technology;
import technologies.TechnologyDAOImpl;

/**
 *
 * @author Lenovo
 */
public class PersonnelGUI extends javax.swing.JFrame {
    RequestDAOImpl requestDAOImpl = new RequestDAOImpl();
    private Timer autoRefreshTimer;
    private ConsumableDAOImpl consumableDAO = new ConsumableDAOImpl();
    private Consumable consumable = null;
    private EquipmentDAOImpl equipmentDAO = new EquipmentDAOImpl();
    private Equipment equipment = null;
    private TechnologyDAOImpl technologyDAO = new TechnologyDAOImpl();
    private Technology technology = null;
    private OthersDAOImpl othersDAO = new OthersDAOImpl();
    private Others others = null;
    
    /**
     * Creates new form PersonnelGUI
     */
    public PersonnelGUI() {
        initComponents();
        refreshTables();
        loadNotifications();
        autoRefreshTimer = new Timer(10000, e -> loadNotifications());
        autoRefreshTimer.start();
        int currentUserId = LoggedInUser.getUserId();
        loadDashboard(currentUserId);
    
    }
    
    public void dispose() {
        if (autoRefreshTimer != null && autoRefreshTimer.isRunning()) {
            autoRefreshTimer.stop();
        }
        super.dispose();
    }

    private void loadDashboard(int userId) {
    DefaultTableModel model = (DefaultTableModel) dashboardTBL.getModel();
    model.setRowCount(0); // clear the table first

    ArrayList<Request> otherRequests = requestDAOImpl.readRequestsOfOthers(userId);

    for (Request request : otherRequests) {
        model.addRow(new Object[]{
            request.getRequest_id(),
            request.getRequest_info(),
            request.getRequested_by(),
            request.getPriority(),
            request.getRequest_type()
        });
    }

}

    
    private void loadNotifications() {
    ResponseDAO responseDAO = new ResponseDAOImpl();
    ArrayList<Response> responses = responseDAO.getAllResponses(LoggedInUser.getUserId());

    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new String[] {
        "Request ID", "Status Update", "Comment", "Schedule", "Date Responded"
    });

    for (Response response : responses) {
        model.addRow(new Object[] {
            response.getRequest_id(),
            response.getStatus_update(),
            response.getComment(),
            response.getSchedule_Datetime(),
            response.getDate_responded()
        });
    }

    notificationsTBL.setModel(model);
}
    public void refreshConsumableTBL(){
        DefaultTableModel model = (DefaultTableModel) consumableTBL.getModel();
        model.setRowCount(0);

        for(Consumable c : consumableDAO.read_all()){
            model.addRow(new Object[]{
                c.getConsumableSupply_id(),
                c.getName(),
                c.getQuantity(),
                c.getPreferences()
            });
        }
    }

    public void refreshEquipmentTBL(){
        DefaultTableModel model = (DefaultTableModel) equipmentTBL.getModel();
        model.setRowCount(0);

        for(Equipment e : equipmentDAO.read_all()){
            model.addRow(new Object[]{
                e.getEquipmentSupply_id(),
                e.getName(),
                e.getQuantity(),
                e.getPreferences()
            });
        }
    }

    public void refreshTechnologyTBL(){
        DefaultTableModel model = (DefaultTableModel) technologyTBL.getModel();
        model.setRowCount(0);

        for(Technology t : technologyDAO.read_all()){
            model.addRow(new Object[]{
                t.getTechnologySupply_id(),
                t.getName(),
                t.getQuantity(),
                t.getPreferences()
            });
        }
    }

    public void refreshOthersTBL(){
        DefaultTableModel model = (DefaultTableModel) othersTBL.getModel();
        model.setRowCount(0);

        for(Others o : othersDAO.read_all()){
            model.addRow(new Object[]{
                o.getOthersSupply_id(),
                o.getName(),
                o.getQuantity(),
                o.getPreferences()
            });
        }
    }

    
    public void refreshTables(){
        refreshConsumableTBL();
        refreshEquipmentTBL();
        refreshTechnologyTBL();
        refreshOthersTBL();
    }
    
    public void searchConsumableTable(String str){
        DefaultTableModel model = (DefaultTableModel) consumableTBL.getModel();
        model.setRowCount(0);

        for(Consumable c : consumableDAO.search(str)){
            model.addRow(new Object[]{
                c.getConsumableSupply_id(),
                c.getName(),
                c.getQuantity(),
                c.getPreferences()
            });
        }
    }
    
    public void searchEquipmentTable(String str){
        DefaultTableModel model = (DefaultTableModel) equipmentTBL.getModel();
        model.setRowCount(0);

        for(Equipment e : equipmentDAO.search(str)){
            model.addRow(new Object[]{
                e.getEquipmentSupply_id(),
                e.getName(),
                e.getQuantity(),
                e.getPreferences()
            });
        }
    }
    
    public void searchTechnologyTable(String str){
        DefaultTableModel model = (DefaultTableModel) technologyTBL.getModel();
        model.setRowCount(0);

        for(Technology t : technologyDAO.search(str)){
            model.addRow(new Object[]{
                t.getTechnologySupply_id(),
                t.getName(),
                t.getQuantity(),
                t.getPreferences()
            });
        }
    }
    
    public void searchOthersTable(String str){
        DefaultTableModel model = (DefaultTableModel) othersTBL.getModel();
        model.setRowCount(0);

        for(Others o : othersDAO.search(str)){
            model.addRow(new Object[]{
                o.getOthersSupply_id(),
                o.getName(),
                o.getQuantity(),
                o.getPreferences()
            });
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        DashboardP = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        welcomeLBL = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        dashboardTBL = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        SubmitRequestP = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        serviceRequestInfoTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        serviceLocationTF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        servicePriorityCB = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        serviceDescriptionTF = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        serviceSubmitBTN = new javax.swing.JButton();
        serviceCancelBTN = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        serviceRequestedByTF = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        supplyRequestInfoTF = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        supplyDescriptionTF = new javax.swing.JTextArea();
        supplyQuantityTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        supplyLocationTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        supplyRequestedByTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        supplyPriorityCB = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        supplyPurposeTF = new javax.swing.JTextArea();
        supplySubmitBTN = new javax.swing.JButton();
        supplyCancelBTN = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        SupplyListP = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        consumableTBL = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        consumableSearchTF = new javax.swing.JTextField();
        consumableSearchBTN = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        equipmentSearchTF = new javax.swing.JTextField();
        equipmentSearchBTN = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        equipmentTBL = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        technologyTBL = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        technologySearchTF = new javax.swing.JTextField();
        technologySearchBTN = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        othersTBL = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        othersSearchTF = new javax.swing.JTextField();
        othersSearchBTN = new javax.swing.JButton();
        NotificationP = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        notificationsTBL = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        logoutMI = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(204, 204, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        welcomeLBL.setFont(new java.awt.Font("Sitka Display", 2, 36)); // NOI18N
        welcomeLBL.setText("Welcome to MSM System!");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(welcomeLBL)
                .addGap(179, 179, 179))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(welcomeLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dashboardTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Request ID", "Request Info", "Requested By", "Priority", "Request Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(dashboardTBL);
        if (dashboardTBL.getColumnModel().getColumnCount() > 0) {
            dashboardTBL.getColumnModel().getColumn(0).setResizable(false);
            dashboardTBL.getColumnModel().getColumn(1).setResizable(false);
            dashboardTBL.getColumnModel().getColumn(2).setResizable(false);
            dashboardTBL.getColumnModel().getColumn(3).setResizable(false);
            dashboardTBL.getColumnModel().getColumn(4).setResizable(false);
        }

        jScrollPane9.setViewportView(jScrollPane11);

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mrm/output-onlinepngtools.png"))); // NOI18N

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 2, 14)); // NOI18N
        jLabel22.setText("In the table, you can see the requests of other personnels...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DashboardPLayout = new javax.swing.GroupLayout(DashboardP);
        DashboardP.setLayout(DashboardPLayout);
        DashboardPLayout.setHorizontalGroup(
            DashboardPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DashboardPLayout.setVerticalGroup(
            DashboardPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Dashboard", DashboardP);

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(204, 204, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Request Info:");

        jLabel7.setText("Location:");

        servicePriorityCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High", "Urgent" }));

        jLabel9.setText("Priority:");

        serviceDescriptionTF.setColumns(20);
        serviceDescriptionTF.setRows(5);
        jScrollPane2.setViewportView(serviceDescriptionTF);

        jLabel6.setText("Description:");

        serviceSubmitBTN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        serviceSubmitBTN.setText("Submit");
        serviceSubmitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceSubmitBTNActionPerformed(evt);
            }
        });

        serviceCancelBTN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        serviceCancelBTN.setText("Cancel");
        serviceCancelBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceCancelBTNActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("SERVICE REQUEST FORM");

        serviceRequestedByTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceRequestedByTFActionPerformed(evt);
            }
        });

        jLabel12.setText("Requested by:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(servicePriorityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serviceLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(serviceRequestedByTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                        .addComponent(serviceRequestInfoTF, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 77, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(serviceCancelBTN)
                    .addComponent(serviceSubmitBTN))
                .addGap(41, 41, 41))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serviceRequestedByTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serviceRequestInfoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(serviceLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(servicePriorityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(serviceSubmitBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceCancelBTN)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(jLabel8)
                .addContainerGap(298, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(206, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Services", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(204, 204, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setPreferredSize(new java.awt.Dimension(500, 500));

        supplyRequestInfoTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplyRequestInfoTFActionPerformed(evt);
            }
        });

        jLabel14.setText("Request Info:");

        jLabel15.setText("Description:");

        supplyDescriptionTF.setColumns(20);
        supplyDescriptionTF.setRows(5);
        jScrollPane4.setViewportView(supplyDescriptionTF);

        jLabel3.setText("Quantity:");

        jLabel16.setText("Location:");

        jLabel4.setText("Purpose/Reason:");

        supplyRequestedByTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplyRequestedByTFActionPerformed(evt);
            }
        });

        jLabel1.setText("Requested by:");

        supplyPriorityCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High", "Urgent" }));

        jLabel10.setText("Priority:");

        supplyPurposeTF.setColumns(20);
        supplyPurposeTF.setRows(5);
        jScrollPane5.setViewportView(supplyPurposeTF);

        supplySubmitBTN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        supplySubmitBTN.setText("Submit");
        supplySubmitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplySubmitBTNActionPerformed(evt);
            }
        });

        supplyCancelBTN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        supplyCancelBTN.setText("Cancel");
        supplyCancelBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplyCancelBTNActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SUPPLY REQUEST FORM");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(supplyRequestedByTF, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplyLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(32, 32, 32)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supplyQuantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(supplyRequestInfoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(supplyPriorityCB, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(21, 21, 21))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplyCancelBTN, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(supplySubmitBTN, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(39, 39, 39))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(supplyRequestedByTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(supplyLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(supplyRequestInfoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(supplyPriorityCB, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 29, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(supplyQuantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(supplySubmitBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supplyCancelBTN)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Supplies", jPanel3);

        javax.swing.GroupLayout SubmitRequestPLayout = new javax.swing.GroupLayout(SubmitRequestP);
        SubmitRequestP.setLayout(SubmitRequestPLayout);
        SubmitRequestPLayout.setHorizontalGroup(
            SubmitRequestPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubmitRequestPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        SubmitRequestPLayout.setVerticalGroup(
            SubmitRequestPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane1.addTab("Submit Request", SubmitRequestP);

        consumableTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supply ID", "Supply Name", "Quantity", "Preferences"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(consumableTBL);

        jLabel13.setText("Search supply:");

        consumableSearchBTN.setText("Search");
        consumableSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumableSearchBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(consumableSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(consumableSearchBTN)
                        .addGap(0, 227, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(consumableSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(consumableSearchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Consumable", jPanel7);

        jLabel17.setText("Search supply:");

        equipmentSearchBTN.setText("Search");
        equipmentSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipmentSearchBTNActionPerformed(evt);
            }
        });

        equipmentTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supply ID", "Supply Name", "Quantity", "Preferences"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(equipmentTBL);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(equipmentSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(equipmentSearchBTN)
                        .addGap(0, 221, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(equipmentSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(equipmentSearchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Classroom Equipment", jPanel10);

        technologyTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supply ID", "Supply Name", "Quantity", "Preferences"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(technologyTBL);

        jLabel18.setText("Search supply:");

        technologySearchBTN.setText("Search");
        technologySearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technologySearchBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(technologySearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(technologySearchBTN)
                        .addGap(0, 221, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(technologySearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(technologySearchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Technology", jPanel11);

        othersTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supply ID", "Supply Name", "Quantity", "Preferences"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(othersTBL);

        jLabel19.setText("Search supply:");

        othersSearchBTN.setText("Search");
        othersSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                othersSearchBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane8)
                        .addContainerGap())
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(othersSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(othersSearchBTN)
                        .addGap(0, 227, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(othersSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(othersSearchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Others", jPanel12);

        javax.swing.GroupLayout SupplyListPLayout = new javax.swing.GroupLayout(SupplyListP);
        SupplyListP.setLayout(SupplyListPLayout);
        SupplyListPLayout.setHorizontalGroup(
            SupplyListPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplyListPLayout.createSequentialGroup()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        SupplyListPLayout.setVerticalGroup(
            SupplyListPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplyListPLayout.createSequentialGroup()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Supply List", SupplyListP);

        NotificationP.setPreferredSize(new java.awt.Dimension(890, 850));

        notificationsTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Request ID", "Status Update", "Comment", "Schedule", "Date Responded"
            }
        ));
        jScrollPane10.setViewportView(notificationsTBL);

        javax.swing.GroupLayout NotificationPLayout = new javax.swing.GroupLayout(NotificationP);
        NotificationP.setLayout(NotificationPLayout);
        NotificationPLayout.setHorizontalGroup(
            NotificationPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NotificationPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
                .addContainerGap())
        );
        NotificationPLayout.setVerticalGroup(
            NotificationPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NotificationPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Notification", NotificationP);

        jLabel20.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\dashboard (6) (1).png")); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\inventory (3) (1).png")); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\icons8-notification-24 (1).png")); // NOI18N

        jLabel23.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\icons8-submit-document-24.png")); // NOI18N

        jMenu1.setText("File");

        logoutMI.setText("Logout");
        logoutMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMIActionPerformed(evt);
            }
        });
        jMenu1.add(logoutMI);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1051, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addGap(8, 8, 8)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void supplyCancelBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplyCancelBTNActionPerformed
        supplyRequestInfoTF.setText("");
        supplyQuantityTF.setText("");
        supplyDescriptionTF.setText("");
        supplyPurposeTF.setText("");
        supplyLocationTF.setText("");
        supplyRequestedByTF.setText("");
        supplyPriorityCB.setToolTipText("");
    }//GEN-LAST:event_supplyCancelBTNActionPerformed

    private void supplySubmitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplySubmitBTNActionPerformed
        String request_info = supplyRequestInfoTF.getText();
        String quantity = supplyQuantityTF.getText();
        String description = supplyDescriptionTF.getText();
        String purpose = supplyPurposeTF.getText();
        String requested_by = supplyRequestedByTF.getText();
        String location = supplyLocationTF.getText();
        String priority = (supplyPriorityCB.getSelectedItem() != null) ? supplyPriorityCB.getSelectedItem().toString() : "";

        if (request_info.isEmpty() || quantity.isEmpty() || purpose.isEmpty() || requested_by.isEmpty() ||location.isEmpty()|| priority.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Please complete all fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(null,
            "This will save the supply request. Are you sure?",
            "Confirmation",
            JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            int user_id = LoggedInUser.getUserId();
            Request request = new Request(0, user_id, request_info, "Supply", quantity, description, purpose,requested_by, location, priority, "");

            if (requestDAOImpl.create(request)) {
                JOptionPane.showMessageDialog(null,
                    "Supply request submitted!",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);

                supplyRequestInfoTF.setText("");
                supplyQuantityTF.setText("");
                supplyDescriptionTF.setText("");
                supplyPurposeTF.setText("");
                supplyRequestedByTF.setText("");
                supplyLocationTF.setText("");
                supplyPriorityCB.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Supply request failed to submit.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_supplySubmitBTNActionPerformed

    private void supplyRequestedByTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplyRequestedByTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supplyRequestedByTFActionPerformed

    private void supplyRequestInfoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplyRequestInfoTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supplyRequestInfoTFActionPerformed

    private void serviceRequestedByTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceRequestedByTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceRequestedByTFActionPerformed

    private void serviceCancelBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceCancelBTNActionPerformed
        serviceRequestedByTF.setText("");
        serviceRequestInfoTF.setText("");
        serviceDescriptionTF.setText("");
        serviceLocationTF.setText("");
        servicePriorityCB.setToolTipText("");
    }//GEN-LAST:event_serviceCancelBTNActionPerformed

    private void serviceSubmitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceSubmitBTNActionPerformed

        String request_info = serviceRequestInfoTF.getText().trim();
        String description = serviceDescriptionTF.getText().trim();
        String location = serviceLocationTF.getText().trim();
        String requested_by = serviceRequestedByTF.getText().trim();
        String priority = (servicePriorityCB.getSelectedItem() != null) ? servicePriorityCB.getSelectedItem().toString() : "";

        if (request_info.isEmpty() || description.isEmpty() || location.isEmpty() ||requested_by.isEmpty()|| priority.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Please complete all fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(null,
            "This will save the service request. Are you sure?",
            "Confirmation",
            JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            int user_id = LoggedInUser.getUserId();

            Request request = new Request(0, user_id, request_info, "Service", "",description, "",requested_by, location, priority, "");

            if (requestDAOImpl.create(request)) {
                JOptionPane.showMessageDialog(null,
                    "Service request submitted!",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
                serviceRequestedByTF.setText("");
                serviceRequestInfoTF.setText("");
                serviceDescriptionTF.setText("");
                serviceLocationTF.setText("");
                servicePriorityCB.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Service request failed to submit.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_serviceSubmitBTNActionPerformed

    private void consumableSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumableSearchBTNActionPerformed
    String str = consumableSearchTF.getText();
        
        searchConsumableTable(str);
    }//GEN-LAST:event_consumableSearchBTNActionPerformed

    private void equipmentSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipmentSearchBTNActionPerformed
    String str = equipmentSearchTF.getText();
        
        searchEquipmentTable(str);
    }//GEN-LAST:event_equipmentSearchBTNActionPerformed

    private void technologySearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technologySearchBTNActionPerformed
    String str = technologySearchTF.getText();
        
        searchTechnologyTable(str);
    }//GEN-LAST:event_technologySearchBTNActionPerformed

    private void othersSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_othersSearchBTNActionPerformed
    String str = othersSearchTF.getText();
        
        searchOthersTable(str); 
    }//GEN-LAST:event_othersSearchBTNActionPerformed

    private void logoutMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMIActionPerformed
    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_logoutMIActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PersonnelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PersonnelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PersonnelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PersonnelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PersonnelGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DashboardP;
    private javax.swing.JPanel NotificationP;
    private javax.swing.JPanel SubmitRequestP;
    private javax.swing.JPanel SupplyListP;
    private javax.swing.JButton consumableSearchBTN;
    private javax.swing.JTextField consumableSearchTF;
    private javax.swing.JTable consumableTBL;
    private javax.swing.JTable dashboardTBL;
    private javax.swing.JButton equipmentSearchBTN;
    private javax.swing.JTextField equipmentSearchTF;
    private javax.swing.JTable equipmentTBL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JMenuItem logoutMI;
    private javax.swing.JTable notificationsTBL;
    private javax.swing.JButton othersSearchBTN;
    private javax.swing.JTextField othersSearchTF;
    private javax.swing.JTable othersTBL;
    private javax.swing.JButton serviceCancelBTN;
    private javax.swing.JTextArea serviceDescriptionTF;
    private javax.swing.JTextField serviceLocationTF;
    private javax.swing.JComboBox<String> servicePriorityCB;
    private javax.swing.JTextField serviceRequestInfoTF;
    private javax.swing.JTextField serviceRequestedByTF;
    private javax.swing.JButton serviceSubmitBTN;
    private javax.swing.JButton supplyCancelBTN;
    private javax.swing.JTextArea supplyDescriptionTF;
    private javax.swing.JTextField supplyLocationTF;
    private javax.swing.JComboBox<String> supplyPriorityCB;
    private javax.swing.JTextArea supplyPurposeTF;
    private javax.swing.JTextField supplyQuantityTF;
    private javax.swing.JTextField supplyRequestInfoTF;
    private javax.swing.JTextField supplyRequestedByTF;
    private javax.swing.JButton supplySubmitBTN;
    private javax.swing.JButton technologySearchBTN;
    private javax.swing.JTextField technologySearchTF;
    private javax.swing.JTable technologyTBL;
    private javax.swing.JLabel welcomeLBL;
    // End of variables declaration//GEN-END:variables
}
