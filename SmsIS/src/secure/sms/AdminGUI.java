/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package secure.sms;

import all_requests.Request;
import java.util.ArrayList;
import all_requests.RequestDAO;
import all_requests.RequestDAOImpl;
import consumables.Consumable;
import consumables.ConsumableDAOImpl;
import equipments.Equipment;
import equipments.EquipmentDAOImpl;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import others.Others;
import others.OthersDAOImpl;
import technologies.Technology;
import technologies.TechnologyDAOImpl;
/**
 *
 * @author Lenovo
 */
public class AdminGUI extends javax.swing.JFrame {
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
    private boolean isUpdateMode = false;
    private int currentConsumableId = -1;
    private int currentEquipmentId = -1;
    private int currentTechnologyId = -1;
    private int currentOthersId = -1;
    /**
     * Creates new form AdminGUI
     */
    public AdminGUI() {
        initComponents();
        refreshTBL();
        refreshTables();
        refreshFilteredRequests();
        typeCB.setSelectedItem("All");
        priorityCB.setSelectedItem("All");
        reloadTabs();
        autoRefreshTimer = new Timer(10000, e -> reloadTabs());
        autoRefreshTimer.start();
        dashboardCounts();
    }
    
    public void dispose() {
        if (autoRefreshTimer != null && autoRefreshTimer.isRunning()) {
            autoRefreshTimer.stop();
        }
        super.dispose();
    }
    
  
    public void refreshTBL() {
        ArrayList<Request> requests = requestDAOImpl.read_all();
        DefaultTableModel model = (DefaultTableModel) requestsTBL.getModel();
        model.setRowCount(0);

        for (Request request : requests) {
            model.addRow(new Object[]{
                request.getRequest_id(),
                request.getUser_id(),
                request.getRequested_by(),
                request.getRequest_info(),
                request.getRequest_type(),
                request.getPriority(),
                request.getDate_created()
            });

        }
    }
    
    private void refreshFilteredRequests() {
        String selectedType = typeCB.getSelectedItem().toString();
        String selectedPriority = priorityCB.getSelectedItem().toString();
        
        DefaultTableModel model = (DefaultTableModel) requestsTBL.getModel();
        model.setRowCount(0); 
        ArrayList<Request> requests = requestDAOImpl.readFiltered(selectedType,selectedPriority);

        for (Request request : requests) {
            model.addRow(new Object[] {
                request.getRequest_id(),
                request.getUser_id(),
                request.getRequested_by(),
                request.getRequest_type(),
                request.getRequest_info(),
                request.getPriority(),
                request.getDate_created()
            });
        }
    }
    
    public void refreshRequestsByStatus(JTable table, String status) {
        RequestDAOImpl requestDAOImpl = new RequestDAOImpl();
        ArrayList<Request> requests = requestDAOImpl.readByStatus(status);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 

        for (Request request : requests) {
            model.addRow(new Object[]{
                request.getRequest_id(),
                request.getUser_id(),
                request.getRequested_by(),
                request.getRequest_info(),
                request.getRequest_type(),
                request.getPriority(),
                request.getDate_created()
            });
        }
    }
    
    public void reloadTabs() {
    // --- Pending Tab ---
    ArrayList<Request> pendingRequests = requestDAOImpl.readByNoResponseOrStatus("Pending");
    DefaultTableModel pendingModel = (DefaultTableModel) requestsTBL.getModel();
    pendingModel.setRowCount(0);
    for (Request req : pendingRequests) {
        pendingModel.addRow(new Object[]{
            req.getRequest_id(),
            req.getUser_id(),
            req.getRequested_by(),
            req.getRequest_info(),
            req.getRequest_type(),
            req.getPriority(),
            req.getDate_created()
        });
    }

    // --- In Progress Tab ---
    ArrayList<Request> inProgress = requestDAOImpl.readByStatus("In Progress");
    DefaultTableModel inProgressModel = (DefaultTableModel) inProgressTBL.getModel();
    inProgressModel.setRowCount(0);
    for (Request req : inProgress) {
        inProgressModel.addRow(new Object[]{
            req.getRequest_id(),
            req.getUser_id(),
            req.getRequested_by(),
            req.getRequest_info(),
            req.getRequest_type(),
            req.getPriority(),
            req.getDate_created()
        });
    }

    // --- Record Tab (Completed + Rejected) ---
    ArrayList<Request> records = requestDAOImpl.readByStatus("Completed");
    records.addAll(requestDAOImpl.readByStatus("Rejected"));

    DefaultTableModel recordModel = (DefaultTableModel) recordTBL.getModel();
    recordModel.setRowCount(0);
    for (Request req : records) {
        recordModel.addRow(new Object[]{
            req.getRequest_id(),
            req.getUser_id(),
            req.getRequested_by(),
            req.getRequest_info(),
            req.getRequest_type(),
            req.getPriority(),
            req.getDate_created()
        });
    }
}
    private void dashboardCounts() {
        int pendingCount = RequestDAOImpl.countByStatus("Pending");
        int inProgressCount = RequestDAOImpl.countByStatus("In Progress");
        int completedCount = RequestDAOImpl.countByStatus("Completed");

        pendingCountLBL.setText(pendingCount + " Requests");
        inprogressCountLBL.setText(inProgressCount + " Requests");
        completedCountLBL.setText(completedCount + " Requests");
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
    
     public void resetConsumableTab(){
        refreshConsumableTBL();
        consumableNameTF.setText("");
        consumableQuantityTF.setText("");
        consumablePreferencesTF.setText("");
        consumableSaveBTN.setText("Save");
    }
     
     public void resetEquipmentTab(){
        refreshEquipmentTBL();
        equipmentNameTF.setText("");
        equipmentQuantityTF.setText("");
        equipmentPreferencesTF.setText("");
        equipmentSaveBTN.setText("Save");
    }
     
     public void resetTechnologyTab(){
        refreshTechnologyTBL();
        technologyNameTF.setText("");
        technologyQuantityTF.setText("");
        technologyPreferencesTF.setText("");
        technologySaveBTN.setText("Save");
    }
     
     public void resetOthersTab(){
        refreshOthersTBL();
        othersNameTF.setText("");
        othersQuantityTF.setText("");
        othersPreferencesTF.setText("");
        othersSaveBTN.setText("Save");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        consumablePUM = new javax.swing.JPopupMenu();
        consumableUpdate = new javax.swing.JMenuItem();
        consumableDelete = new javax.swing.JMenuItem();
        equipmentPUM = new javax.swing.JPopupMenu();
        equipmentUpdate = new javax.swing.JMenuItem();
        equipmentDelete = new javax.swing.JMenuItem();
        technologyPUM = new javax.swing.JPopupMenu();
        technologyUpdate = new javax.swing.JMenuItem();
        technologyDelete = new javax.swing.JMenuItem();
        othersPUM = new javax.swing.JPopupMenu();
        othersUpdate = new javax.swing.JMenuItem();
        othersDelete = new javax.swing.JMenuItem();
        adminTP = new javax.swing.JTabbedPane();
        DashboardP = new javax.swing.JPanel();
        dashboardPanel = new javax.swing.JPanel();
        pendingPanel = new javax.swing.JPanel();
        pendingstatusLabel = new javax.swing.JLabel();
        pendingCountLBL = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        inprogressPanel = new javax.swing.JPanel();
        pendingstatusLabel1 = new javax.swing.JLabel();
        inprogressCountLBL = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pendingstatusLabel2 = new javax.swing.JLabel();
        completedCountLBL = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        welcomeLBL = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        RequestListP = new javax.swing.JPanel();
        searchTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        typeCB = new javax.swing.JComboBox<>();
        priorityCB = new javax.swing.JComboBox<>();
        searchBTN = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        requestsTBL = new javax.swing.JTable();
        InProgressP = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        inProgressTBL = new javax.swing.JTable();
        RecordP = new javax.swing.JPanel();
        statusCB = new javax.swing.JComboBox<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        recordTBL = new javax.swing.JTable();
        SupplyListP = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        consumableP = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        consumableNameTF = new javax.swing.JTextField();
        consumablePreferencesTF = new javax.swing.JTextField();
        consumableQuantityTF = new javax.swing.JTextField();
        consumableSaveBTN = new javax.swing.JButton();
        consumableCancelTF = new javax.swing.JButton();
        consumableSearchBTN = new javax.swing.JButton();
        consumableSearchTF = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        consumableTBL = new javax.swing.JTable();
        equipmentP = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        equipmentQuantityTF = new javax.swing.JTextField();
        equipmentNameTF = new javax.swing.JTextField();
        equipmentSaveBTN = new javax.swing.JButton();
        equipmentCancelBTN = new javax.swing.JButton();
        equipmentPreferencesTF = new javax.swing.JTextField();
        equipmentSearchBTN = new javax.swing.JButton();
        equipmentSearchTF = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        equipmentTBL = new javax.swing.JTable();
        technologyP = new javax.swing.JPanel();
        technologySearchTF = new javax.swing.JTextField();
        technologySearchBTN = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        technologyNameTF = new javax.swing.JTextField();
        technologyQuantityTF = new javax.swing.JTextField();
        technologySaveBTN = new javax.swing.JButton();
        technologyCancelBTN = new javax.swing.JButton();
        technologyPreferencesTF = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        technologyTBL = new javax.swing.JTable();
        othersP = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        othersNameTF = new javax.swing.JTextField();
        othersQuantityTF = new javax.swing.JTextField();
        othersPreferencesTF = new javax.swing.JTextField();
        othersSaveBTN = new javax.swing.JButton();
        othersCancelBTN = new javax.swing.JButton();
        othersSearchTF = new javax.swing.JTextField();
        othersSearchBTN = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        othersTBL = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        userMI = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        logoutMI = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        consumableUpdate.setText("Update");
        consumableUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumableUpdateActionPerformed(evt);
            }
        });
        consumablePUM.add(consumableUpdate);

        consumableDelete.setText("Delete");
        consumableDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumableDeleteActionPerformed(evt);
            }
        });
        consumablePUM.add(consumableDelete);

        equipmentUpdate.setText("Update");
        equipmentUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipmentUpdateActionPerformed(evt);
            }
        });
        equipmentPUM.add(equipmentUpdate);

        equipmentDelete.setText("Delete");
        equipmentDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipmentDeleteActionPerformed(evt);
            }
        });
        equipmentPUM.add(equipmentDelete);

        technologyUpdate.setText("Update");
        technologyUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technologyUpdateActionPerformed(evt);
            }
        });
        technologyPUM.add(technologyUpdate);

        technologyDelete.setText("Delete");
        technologyDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technologyDeleteActionPerformed(evt);
            }
        });
        technologyPUM.add(technologyDelete);

        othersUpdate.setText("Update");
        othersUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                othersUpdateActionPerformed(evt);
            }
        });
        othersPUM.add(othersUpdate);

        othersDelete.setText("Delete");
        othersDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                othersDeleteActionPerformed(evt);
            }
        });
        othersPUM.add(othersDelete);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        adminTP.setBackground(new java.awt.Color(255, 255, 255));
        adminTP.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        adminTP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        dashboardPanel.setBackground(new java.awt.Color(255, 255, 255));

        pendingPanel.setBackground(new java.awt.Color(255, 204, 204));
        pendingPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pendingPanel.setPreferredSize(new java.awt.Dimension(278, 167));
        pendingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingPanelMouseClicked(evt);
            }
        });

        pendingstatusLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pendingstatusLabel.setText("PENDING");

        pendingCountLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pendingCountLBL.setText("0 Requests");

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mrm/icon/icons8-request-30.png"))); // NOI18N

        javax.swing.GroupLayout pendingPanelLayout = new javax.swing.GroupLayout(pendingPanel);
        pendingPanel.setLayout(pendingPanelLayout);
        pendingPanelLayout.setHorizontalGroup(
            pendingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingPanelLayout.createSequentialGroup()
                .addGroup(pendingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pendingPanelLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(pendingstatusLabel))
                    .addGroup(pendingPanelLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(pendingCountLBL))
                    .addGroup(pendingPanelLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel25)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        pendingPanelLayout.setVerticalGroup(
            pendingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendingstatusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pendingCountLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        inprogressPanel.setBackground(new java.awt.Color(255, 255, 153));
        inprogressPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inprogressPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inprogressPanelMouseClicked(evt);
            }
        });

        pendingstatusLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pendingstatusLabel1.setText("IN PROGRESS");

        inprogressCountLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        inprogressCountLBL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inprogressCountLBL.setText("0 Requests");

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mrm/icon/icons8-pending-30.png"))); // NOI18N

        javax.swing.GroupLayout inprogressPanelLayout = new javax.swing.GroupLayout(inprogressPanel);
        inprogressPanel.setLayout(inprogressPanelLayout);
        inprogressPanelLayout.setHorizontalGroup(
            inprogressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inprogressPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(inprogressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inprogressCountLBL)
                    .addComponent(pendingstatusLabel1))
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inprogressPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(114, 114, 114))
        );
        inprogressPanelLayout.setVerticalGroup(
            inprogressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inprogressPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendingstatusLabel1)
                .addGap(42, 42, 42)
                .addComponent(inprogressCountLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        pendingstatusLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pendingstatusLabel2.setText("COMPLETED");

        completedCountLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        completedCountLBL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        completedCountLBL.setText("0 Requests");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mrm/icon/icons8-task-completed-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(114, 114, 114))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(completedCountLBL)
                    .addComponent(pendingstatusLabel2))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendingstatusLabel2)
                .addGap(43, 43, 43)
                .addComponent(completedCountLBL)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pendingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inprogressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inprogressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pendingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(312, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(204, 204, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        welcomeLBL.setFont(new java.awt.Font("Sitka Display", 2, 36)); // NOI18N
        welcomeLBL.setText("Welcome to MSM System!");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(welcomeLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(welcomeLBL)
                .addGap(23, 23, 23))
        );

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mrm/icon/output-onlinepngtools.png"))); // NOI18N

        javax.swing.GroupLayout DashboardPLayout = new javax.swing.GroupLayout(DashboardP);
        DashboardP.setLayout(DashboardPLayout);
        DashboardPLayout.setHorizontalGroup(
            DashboardPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DashboardPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DashboardPLayout.createSequentialGroup()
                        .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(DashboardPLayout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        DashboardPLayout.setVerticalGroup(
            DashboardPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DashboardPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        adminTP.addTab("Dashboard", DashboardP);

        RequestListP.setPreferredSize(new java.awt.Dimension(494, 299));

        searchTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTFActionPerformed(evt);
            }
        });

        jLabel1.setText("Search:");

        jLabel2.setText("Filter:");

        typeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Service", "Supply" }));
        typeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeCBActionPerformed(evt);
            }
        });

        priorityCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Low", "Medium", "High", "Urgent" }));
        priorityCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priorityCBActionPerformed(evt);
            }
        });

        searchBTN.setText("Search");
        searchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBTNActionPerformed(evt);
            }
        });

        jLabel23.setText("Type:");

        jLabel24.setText("Priority:");

        requestsTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                " Request ID", "User ID", "Requested by", "Request Info", "Type", "Priority ", "Created"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        requestsTBL.setMinimumSize(new java.awt.Dimension(375, 80));
        requestsTBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestsTBLMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(requestsTBL);
        if (requestsTBL.getColumnModel().getColumnCount() > 0) {
            requestsTBL.getColumnModel().getColumn(0).setResizable(false);
            requestsTBL.getColumnModel().getColumn(1).setResizable(false);
            requestsTBL.getColumnModel().getColumn(2).setResizable(false);
            requestsTBL.getColumnModel().getColumn(3).setResizable(false);
            requestsTBL.getColumnModel().getColumn(4).setResizable(false);
            requestsTBL.getColumnModel().getColumn(5).setResizable(false);
            requestsTBL.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout RequestListPLayout = new javax.swing.GroupLayout(RequestListP);
        RequestListP.setLayout(RequestListPLayout);
        RequestListPLayout.setHorizontalGroup(
            RequestListPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RequestListPLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBTN)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addGap(11, 11, 11)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priorityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
            .addGroup(RequestListPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        RequestListPLayout.setVerticalGroup(
            RequestListPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RequestListPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RequestListPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(typeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priorityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBTN)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        adminTP.addTab("Request List", RequestListP);

        inProgressTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                " Request ID", "User ID", "Requested by", "Request Info", "Type", "Priority ", "Created"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        inProgressTBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inProgressTBLMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(inProgressTBL);
        if (inProgressTBL.getColumnModel().getColumnCount() > 0) {
            inProgressTBL.getColumnModel().getColumn(0).setResizable(false);
            inProgressTBL.getColumnModel().getColumn(1).setResizable(false);
            inProgressTBL.getColumnModel().getColumn(2).setResizable(false);
            inProgressTBL.getColumnModel().getColumn(3).setResizable(false);
            inProgressTBL.getColumnModel().getColumn(4).setResizable(false);
            inProgressTBL.getColumnModel().getColumn(5).setResizable(false);
            inProgressTBL.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout InProgressPLayout = new javax.swing.GroupLayout(InProgressP);
        InProgressP.setLayout(InProgressPLayout);
        InProgressPLayout.setHorizontalGroup(
            InProgressPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InProgressPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
                .addContainerGap())
        );
        InProgressPLayout.setVerticalGroup(
            InProgressPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InProgressPLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        adminTP.addTab("In Progress", InProgressP);

        statusCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Completed", "Rejected" }));
        statusCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusCBActionPerformed(evt);
            }
        });

        recordTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                " Request ID", "User ID", "Requested by", "Request Info", "Type", "Priority ", "Created"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recordTBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordTBLMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(recordTBL);
        if (recordTBL.getColumnModel().getColumnCount() > 0) {
            recordTBL.getColumnModel().getColumn(0).setResizable(false);
            recordTBL.getColumnModel().getColumn(1).setResizable(false);
            recordTBL.getColumnModel().getColumn(2).setResizable(false);
            recordTBL.getColumnModel().getColumn(3).setResizable(false);
            recordTBL.getColumnModel().getColumn(4).setResizable(false);
            recordTBL.getColumnModel().getColumn(5).setResizable(false);
            recordTBL.getColumnModel().getColumn(6).setResizable(false);
        }

        jScrollPane9.setViewportView(jScrollPane7);

        javax.swing.GroupLayout RecordPLayout = new javax.swing.GroupLayout(RecordP);
        RecordP.setLayout(RecordPLayout);
        RecordPLayout.setHorizontalGroup(
            RecordPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecordPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RecordPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RecordPLayout.createSequentialGroup()
                        .addComponent(statusCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE))
                .addContainerGap())
        );
        RecordPLayout.setVerticalGroup(
            RecordPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecordPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminTP.addTab("Record", RecordP);

        jLabel5.setText("Search supply:");

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Supplies Entry/Update Form"));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel3.setText("Fill out the fields below...");

        jLabel4.setText("Supply Name:");

        jLabel6.setText("Quantity:");

        jLabel7.setText("Preferences");

        consumableQuantityTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumableQuantityTFActionPerformed(evt);
            }
        });

        consumableSaveBTN.setText("Save");
        consumableSaveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumableSaveBTNActionPerformed(evt);
            }
        });

        consumableCancelTF.setText("Cancel");
        consumableCancelTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumableCancelTFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(0, 45, Short.MAX_VALUE)
                                .addComponent(consumableSaveBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(consumableCancelTF))
                            .addComponent(consumableQuantityTF)
                            .addComponent(consumablePreferencesTF)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consumableNameTF)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(consumableNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(consumableQuantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(consumablePreferencesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(consumableCancelTF)
                    .addComponent(consumableSaveBTN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        consumableSearchBTN.setText("Search");
        consumableSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumableSearchBTNActionPerformed(evt);
            }
        });

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
        consumableTBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                consumableTBLMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(consumableTBL);
        if (consumableTBL.getColumnModel().getColumnCount() > 0) {
            consumableTBL.getColumnModel().getColumn(0).setResizable(false);
            consumableTBL.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout consumablePLayout = new javax.swing.GroupLayout(consumableP);
        consumableP.setLayout(consumablePLayout);
        consumablePLayout.setHorizontalGroup(
            consumablePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consumablePLayout.createSequentialGroup()
                .addGroup(consumablePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(consumablePLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(consumableSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consumableSearchBTN))
                    .addGroup(consumablePLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        consumablePLayout.setVerticalGroup(
            consumablePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consumablePLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(consumablePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(consumableSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(consumableSearchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(consumablePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addGroup(consumablePLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPane2.addTab("Consumable", consumableP);

        jLabel8.setText("Search supply:");

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Supplies Entry/Update Form"));
        jPanel11.setPreferredSize(new java.awt.Dimension(298, 205));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel9.setText("Fill out the fields below...");

        jLabel10.setText("Supply Name:");

        jLabel11.setText("Quantity:");

        jLabel12.setText("Preferences");

        equipmentSaveBTN.setText("Save");
        equipmentSaveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipmentSaveBTNActionPerformed(evt);
            }
        });

        equipmentCancelBTN.setText("Cancel");
        equipmentCancelBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipmentCancelBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(20, 20, 20)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addComponent(equipmentSaveBTN)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(equipmentCancelBTN))
                                    .addComponent(equipmentPreferencesTF, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(equipmentQuantityTF, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                    .addComponent(equipmentNameTF)))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(equipmentNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(equipmentQuantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(equipmentPreferencesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(equipmentCancelBTN)
                    .addComponent(equipmentSaveBTN))
                .addContainerGap(37, Short.MAX_VALUE))
        );

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
        equipmentTBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                equipmentTBLMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(equipmentTBL);

        javax.swing.GroupLayout equipmentPLayout = new javax.swing.GroupLayout(equipmentP);
        equipmentP.setLayout(equipmentPLayout);
        equipmentPLayout.setHorizontalGroup(
            equipmentPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equipmentPLayout.createSequentialGroup()
                .addGroup(equipmentPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(equipmentPLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8)
                        .addGap(369, 369, 369)
                        .addComponent(equipmentSearchBTN))
                    .addGroup(equipmentPLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(129, Short.MAX_VALUE))
            .addGroup(equipmentPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(equipmentPLayout.createSequentialGroup()
                    .addGap(98, 98, 98)
                    .addComponent(equipmentSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(605, Short.MAX_VALUE)))
        );
        equipmentPLayout.setVerticalGroup(
            equipmentPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equipmentPLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(equipmentPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(equipmentSearchBTN)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(equipmentPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addGroup(equipmentPLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(equipmentPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(equipmentPLayout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(equipmentSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(566, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Classroom Equipment", equipmentP);

        technologySearchBTN.setText("Search");
        technologySearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technologySearchBTNActionPerformed(evt);
            }
        });

        jLabel13.setText("Search supply:");

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Supplies Entry/Update Form"));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel14.setText("Fill out the fields below...");

        jLabel15.setText("Supply Name:");

        jLabel16.setText("Quantity:");

        jLabel17.setText("Preferences");

        technologySaveBTN.setText("Save");
        technologySaveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technologySaveBTNActionPerformed(evt);
            }
        });

        technologyCancelBTN.setText("Cancel");
        technologyCancelBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technologyCancelBTNActionPerformed(evt);
            }
        });

        technologyPreferencesTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                technologyPreferencesTFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(31, 31, 31)
                        .addComponent(technologyQuantityTF))
                    .addComponent(jLabel14)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(technologyNameTF))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGap(0, 37, Short.MAX_VALUE)
                                .addComponent(technologySaveBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(technologyCancelBTN))
                            .addComponent(technologyPreferencesTF))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(technologyNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(technologyQuantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(technologyPreferencesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(technologyCancelBTN)
                    .addComponent(technologySaveBTN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        technologyTBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                technologyTBLMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(technologyTBL);
        if (technologyTBL.getColumnModel().getColumnCount() > 0) {
            technologyTBL.getColumnModel().getColumn(0).setResizable(false);
            technologyTBL.getColumnModel().getColumn(1).setResizable(false);
            technologyTBL.getColumnModel().getColumn(2).setResizable(false);
            technologyTBL.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout technologyPLayout = new javax.swing.GroupLayout(technologyP);
        technologyP.setLayout(technologyPLayout);
        technologyPLayout.setHorizontalGroup(
            technologyPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(technologyPLayout.createSequentialGroup()
                .addGroup(technologyPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(technologyPLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(technologySearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(technologySearchBTN))
                    .addGroup(technologyPLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(133, 133, 133))
        );
        technologyPLayout.setVerticalGroup(
            technologyPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(technologyPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(technologyPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(technologySearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(technologySearchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(technologyPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                    .addGroup(technologyPLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Technology", technologyP);

        jLabel18.setText("Search supply:");

        jPanel13.setBackground(new java.awt.Color(204, 204, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Supplies Entry/Update Form"));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel19.setText("Fill out the fields below...");

        jLabel20.setText("Supply Name:");

        jLabel21.setText("Quantity:");

        jLabel22.setText("Preferences");

        othersSaveBTN.setText("Save");
        othersSaveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                othersSaveBTNActionPerformed(evt);
            }
        });

        othersCancelBTN.setText("Cancel");
        othersCancelBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                othersCancelBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(othersQuantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(othersNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addComponent(othersSaveBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(othersCancelBTN))
                            .addComponent(othersPreferencesTF, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(othersNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(othersQuantityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(othersPreferencesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(othersCancelBTN)
                    .addComponent(othersSaveBTN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        othersSearchBTN.setText("Search");
        othersSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                othersSearchBTNActionPerformed(evt);
            }
        });

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
        othersTBL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                othersTBLMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(othersTBL);
        if (othersTBL.getColumnModel().getColumnCount() > 0) {
            othersTBL.getColumnModel().getColumn(0).setResizable(false);
            othersTBL.getColumnModel().getColumn(1).setResizable(false);
            othersTBL.getColumnModel().getColumn(2).setResizable(false);
            othersTBL.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout othersPLayout = new javax.swing.GroupLayout(othersP);
        othersP.setLayout(othersPLayout);
        othersPLayout.setHorizontalGroup(
            othersPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(othersPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(othersPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(othersPLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(othersSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(othersSearchBTN))
                    .addGroup(othersPLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(164, Short.MAX_VALUE))
        );
        othersPLayout.setVerticalGroup(
            othersPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(othersPLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(othersPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(othersSearchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(othersSearchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(othersPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                    .addGroup(othersPLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Others", othersP);

        javax.swing.GroupLayout SupplyListPLayout = new javax.swing.GroupLayout(SupplyListP);
        SupplyListP.setLayout(SupplyListPLayout);
        SupplyListPLayout.setHorizontalGroup(
            SupplyListPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplyListPLayout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1056, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        SupplyListPLayout.setVerticalGroup(
            SupplyListPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplyListPLayout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        adminTP.addTab("Supply List", SupplyListP);

        jLabel29.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\dashboard (6) (1).png")); // NOI18N

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mrm/icon/icons8-pending-30 (1).png"))); // NOI18N

        jLabel31.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\icons8-request-30.png")); // NOI18N

        jLabel32.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\icons8-task-completed-30 (1).png")); // NOI18N

        jLabel33.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Icons\\inventory (3) (1).png")); // NOI18N

        jMenu1.setText("File");

        userMI.setText("User");
        userMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userMIActionPerformed(evt);
            }
        });
        jMenu1.add(userMI);
        jMenu1.add(jSeparator1);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel29)
                        .addComponent(jLabel30))
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminTP, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(adminTP, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addGap(9, 9, 9)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void requestsTBLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requestsTBLMouseClicked
    if (evt.getClickCount() == 2 && !evt.isConsumed()) {
        evt.consume();
        int row = requestsTBL.getSelectedRow();

        if (row >= 0) {
            Object idValue = requestsTBL.getValueAt(row, 0);
            if (idValue != null) {
                int requestId = (int) idValue;
                Request selectedRequest = requestDAOImpl.read_one(requestId);
                if (selectedRequest != null) {
                    RequestDetailsGUI dialog = new RequestDetailsGUI(this, selectedRequest);
                    dialog.setVisible(true);
                    
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Request ID selected.");
            }
        }
    }
    }//GEN-LAST:event_requestsTBLMouseClicked
    private void userMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userMIActionPerformed
        CreateUserGUI dialog = new CreateUserGUI(this, true);
        dialog.setVisible(true);     
    }//GEN-LAST:event_userMIActionPerformed

    private void consumableQuantityTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumableQuantityTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consumableQuantityTFActionPerformed

    private void consumableSaveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumableSaveBTNActionPerformed
    String name = consumableNameTF.getText();
    String quantity = consumableQuantityTF.getText();
    String preferences = consumablePreferencesTF.getText();

    if (name.equals("") || quantity.equals("") || preferences.equals("")) {
        JOptionPane.showMessageDialog(null, "All fields are required!", "Message", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (consumableSaveBTN.getText().equals("Save")) {
        int option = JOptionPane.showConfirmDialog(null,
            "This will save the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Consumable consumable = new Consumable(0, name, quantity, preferences);
            if (consumableDAO.create(consumable)) {
                JOptionPane.showMessageDialog(null, "New consumable has been saved!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Consumable was not saved!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        int option = JOptionPane.showConfirmDialog(null,
            "This will update the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            this.consumable.setName(name);
            this.consumable.setQuantity(quantity);
            this.consumable.setPreferences(preferences);

            if (consumableDAO.update(this.consumable.getConsumableSupply_id(), this.consumable)) {
                JOptionPane.showMessageDialog(null, "Consumable has been updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Consumable was not updated!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    resetConsumableTab();
    }//GEN-LAST:event_consumableSaveBTNActionPerformed

    private void consumableCancelTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumableCancelTFActionPerformed
    resetConsumableTab();  
    }//GEN-LAST:event_consumableCancelTFActionPerformed

    private void consumableSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumableSearchBTNActionPerformed
    String str = consumableSearchTF.getText();
        
        searchConsumableTable(str);
    }//GEN-LAST:event_consumableSearchBTNActionPerformed

    private void technologySearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technologySearchBTNActionPerformed
    String str = technologySearchTF.getText();
        
        searchTechnologyTable(str);
    }//GEN-LAST:event_technologySearchBTNActionPerformed

    private void technologyTBLMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_technologyTBLMouseReleased
    if (evt.isPopupTrigger()) { 
            int row = technologyTBL.rowAtPoint(evt.getPoint());
        
            if (row >= 0) {
                technologyTBL.setRowSelectionInterval(row, row); 
            }

            technologyPUM.show(technologyTBL, evt.getX(), evt.getY()); 
        } else {
            System.out.println("Nothing happened!");
        }    
    }//GEN-LAST:event_technologyTBLMouseReleased

    private void technologySaveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technologySaveBTNActionPerformed
    String name = technologyNameTF.getText();
    String quantity = technologyQuantityTF.getText();
    String preferences = technologyPreferencesTF.getText();

    if (name.equals("") || quantity.equals("") || preferences.equals("")) {
        JOptionPane.showMessageDialog(null, "All fields are required!", "Message", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (technologySaveBTN.getText().equals("Save")) {
        int option = JOptionPane.showConfirmDialog(null,
            "This will save the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Technology technology = new Technology(0, name, quantity, preferences);
            if (technologyDAO.create(technology)) {
                JOptionPane.showMessageDialog(null, "New technology has been saved!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Technology was not saved!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        int option = JOptionPane.showConfirmDialog(null,
            "This will update the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            this.technology.setName(name);
            this.technology.setQuantity(quantity);
            this.technology.setPreferences(preferences);

            if (technologyDAO.update(this.technology.getTechnologySupply_id(), this.technology)) {
                JOptionPane.showMessageDialog(null, "Technology has been updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Technology was not updated!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    resetTechnologyTab();
    }//GEN-LAST:event_technologySaveBTNActionPerformed

    private void technologyCancelBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technologyCancelBTNActionPerformed
     resetTechnologyTab();
    }//GEN-LAST:event_technologyCancelBTNActionPerformed

    private void technologyPreferencesTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technologyPreferencesTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_technologyPreferencesTFActionPerformed

    private void othersTBLMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_othersTBLMouseReleased
    if (evt.isPopupTrigger()) { 
            int row = othersTBL.rowAtPoint(evt.getPoint());
        
            if (row >= 0) {
                othersTBL.setRowSelectionInterval(row, row); 
            }

           othersPUM.show(othersTBL, evt.getX(), evt.getY()); 
        } else {
            System.out.println("Nothing happened!");
        }
       
    }//GEN-LAST:event_othersTBLMouseReleased

    private void othersSaveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_othersSaveBTNActionPerformed
    String name = othersNameTF.getText();
    String quantity = othersQuantityTF.getText();
    String preferences = othersPreferencesTF.getText();

    if (name.equals("") || quantity.equals("") || preferences.equals("")) {
        JOptionPane.showMessageDialog(null, "All fields are required!", "Message", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (othersSaveBTN.getText().equals("Save")) {
        int option = JOptionPane.showConfirmDialog(null,
            "This will save the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Others others = new Others(0, name, quantity, preferences);
            if (othersDAO.create(others)) {
                JOptionPane.showMessageDialog(null, "New entry has been saved!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Entry was not saved!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        int option = JOptionPane.showConfirmDialog(null,
            "This will update the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            this.others.setName(name);
            this.others.setQuantity(quantity);
            this.others.setPreferences(preferences);

            if (othersDAO.update(this.others.getOthersSupply_id(), this.others)) {
                JOptionPane.showMessageDialog(null, "Entry has been updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Entry was not updated!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    resetOthersTab();
    
    }//GEN-LAST:event_othersSaveBTNActionPerformed

    private void othersCancelBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_othersCancelBTNActionPerformed
     resetOthersTab();
       
    }//GEN-LAST:event_othersCancelBTNActionPerformed

    private void othersSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_othersSearchBTNActionPerformed
    String str = othersSearchTF.getText();
        
        searchOthersTable(str);    
    }//GEN-LAST:event_othersSearchBTNActionPerformed

    private void consumableUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumableUpdateActionPerformed
    int selectedRow = consumableTBL.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a row to update.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) consumableTBL.getModel();
    int consumable_id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

    // Use your consumableDAO, not supplyDAOImpl
    this.consumable = consumableDAO.read_one(consumable_id);

    consumableNameTF.setText(consumable.getName());
    consumableQuantityTF.setText(String.valueOf(consumable.getQuantity()));
    consumablePreferencesTF.setText(consumable.getPreferences());

    consumableSaveBTN.setText("Update");
    isUpdateMode = true;
    currentConsumableId = consumable_id;
    }//GEN-LAST:event_consumableUpdateActionPerformed

    private void consumableDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumableDeleteActionPerformed
     DefaultTableModel model = (DefaultTableModel) consumableTBL.getModel();
    
    if (consumableTBL.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, 
                "Please select a row first!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    int consumable_id = Integer.parseInt(model.getValueAt(consumableTBL.getSelectedRow(), 0).toString());

    int option = JOptionPane.showConfirmDialog(null, 
            "This will delete the record permanently. Are you sure?", 
            "Confirmation", 
            JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        if (consumableDAO.delete(consumable_id)) {
            JOptionPane.showMessageDialog(null, 
                    "Consumable was deleted!", 
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Consumable was not deleted!", 
                    "Message", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    resetConsumableTab();   
    }//GEN-LAST:event_consumableDeleteActionPerformed

    private void equipmentUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipmentUpdateActionPerformed
     int selectedRow = equipmentTBL.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a row to update.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) equipmentTBL.getModel();
    int equipment_id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

    this.equipment = equipmentDAO.read_one(equipment_id);

    equipmentNameTF.setText(equipment.getName());
    equipmentQuantityTF.setText(String.valueOf(equipment.getQuantity()));
    equipmentPreferencesTF.setText(equipment.getPreferences());

    equipmentSaveBTN.setText("Update");
    isUpdateMode = true;
    currentEquipmentId = equipment_id;   
    }//GEN-LAST:event_equipmentUpdateActionPerformed

    private void equipmentDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipmentDeleteActionPerformed
    DefaultTableModel model = (DefaultTableModel) equipmentTBL.getModel();
    
    if (equipmentTBL.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, 
                "Please select a row first!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    int equipment_id = Integer.parseInt(model.getValueAt(equipmentTBL.getSelectedRow(), 0).toString());

    int option = JOptionPane.showConfirmDialog(null, 
            "This will delete the record permanently. Are you sure?", 
            "Confirmation", 
            JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        if (equipmentDAO.delete(equipment_id)) {
            JOptionPane.showMessageDialog(null, 
                    "Equipment was deleted!", 
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Equipment was not deleted!", 
                    "Message", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    resetEquipmentTab();    
    }//GEN-LAST:event_equipmentDeleteActionPerformed

    private void technologyUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technologyUpdateActionPerformed
    int selectedRow = technologyTBL.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a row to update.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) technologyTBL.getModel();
    int technology_id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

    this.technology = technologyDAO.read_one(technology_id);

    technologyNameTF.setText(technology.getName());
    technologyQuantityTF.setText(String.valueOf(technology.getQuantity()));
    technologyPreferencesTF.setText(technology.getPreferences());

    technologySaveBTN.setText("Update");
    isUpdateMode = true;
    currentTechnologyId = technology_id;    
    }//GEN-LAST:event_technologyUpdateActionPerformed

    private void technologyDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technologyDeleteActionPerformed
    DefaultTableModel model = (DefaultTableModel) technologyTBL.getModel();
    
    if (technologyTBL.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, 
                "Please select a row first!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    int technology_id = Integer.parseInt(model.getValueAt(technologyTBL.getSelectedRow(), 0).toString());

    int option = JOptionPane.showConfirmDialog(null, 
            "This will delete the record permanently. Are you sure?", 
            "Confirmation", 
            JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        if (technologyDAO.delete(technology_id)) {
            JOptionPane.showMessageDialog(null, 
                    "Technology was deleted!", 
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Technology was not deleted!", 
                    "Message", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    resetTechnologyTab();    
    }//GEN-LAST:event_technologyDeleteActionPerformed

    private void othersUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_othersUpdateActionPerformed
    int selectedRow = othersTBL.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a row to update.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) othersTBL.getModel();
    int others_id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

    this.others = othersDAO.read_one(others_id);

    othersNameTF.setText(others.getName());
    othersQuantityTF.setText(String.valueOf(others.getQuantity()));
    othersPreferencesTF.setText(others.getPreferences());

    othersSaveBTN.setText("Update");
    isUpdateMode = true;
    currentOthersId = others_id;
    }//GEN-LAST:event_othersUpdateActionPerformed

    private void othersDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_othersDeleteActionPerformed
    DefaultTableModel model = (DefaultTableModel) othersTBL.getModel();
    
    if (othersTBL.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, 
                "Please select a row first!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    int others_id = Integer.parseInt(model.getValueAt(othersTBL.getSelectedRow(), 0).toString());

    int option = JOptionPane.showConfirmDialog(null, 
            "This will delete the record permanently. Are you sure?", 
            "Confirmation", 
            JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        if (othersDAO.delete(others_id)) {
            JOptionPane.showMessageDialog(null, 
                    "Others item was deleted!", 
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Others item was not deleted!", 
                    "Message", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    resetOthersTab();     
    }//GEN-LAST:event_othersDeleteActionPerformed

    private void searchTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTFActionPerformed

    private void searchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBTNActionPerformed
        String str = searchTF.getText();     
        DefaultTableModel model = (DefaultTableModel) requestsTBL.getModel();
        model.setRowCount(0);
        
        for(Request request : requestDAOImpl.searchRequest(str)){
            model.addRow(new Object[]{
                request.getRequest_id(),
                request.getUser_id(),
                request.getRequested_by(),
                request.getRequest_info(),
                request.getRequest_type(),
                request.getPriority(),
                request.getDate_created()
            });
        }
    }//GEN-LAST:event_searchBTNActionPerformed

    private void typeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeCBActionPerformed
        refreshFilteredRequests();
    }//GEN-LAST:event_typeCBActionPerformed

    private void priorityCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priorityCBActionPerformed
        refreshFilteredRequests();
    }//GEN-LAST:event_priorityCBActionPerformed

    private void inProgressTBLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inProgressTBLMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
        evt.consume(); // prevent further handling
        int row = inProgressTBL.getSelectedRow();

            if (row >= 0) {
                Object idValue = inProgressTBL.getValueAt(row, 0);
                if (idValue != null) {
                    int request_id = (int) idValue;
                    Request selectedRequest = requestDAOImpl.read_one(request_id);
                    if (selectedRequest != null) {
                        RequestDetailsGUI dialog = new RequestDetailsGUI(this, selectedRequest);
                        dialog.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Request ID selected.");
                }
            }
        }

    }//GEN-LAST:event_inProgressTBLMouseClicked

    private void recordTBLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordTBLMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_recordTBLMouseClicked

    private void statusCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusCBActionPerformed
        JComboBox<String> statusFilterCombo = new JComboBox<>(new String[]{"Completed", "Rejected"});
            statusCB.addActionListener(e -> {
            String selected = (String) statusCB.getSelectedItem();
            refreshRequestsByStatus(recordTBL, selected);
        });
    }//GEN-LAST:event_statusCBActionPerformed

    private void consumableTBLMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_consumableTBLMouseReleased
    if (evt.isPopupTrigger()) { 
            int row = consumableTBL.rowAtPoint(evt.getPoint());
        
            if (row >= 0) {
                consumableTBL.setRowSelectionInterval(row, row); 
            }

            consumablePUM.show(consumableTBL, evt.getX(), evt.getY()); 
        } else {
            System.out.println("Nothing happened!");
        }    
    }//GEN-LAST:event_consumableTBLMouseReleased

    private void pendingPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingPanelMouseClicked
    adminTP.setSelectedIndex(1);
    }//GEN-LAST:event_pendingPanelMouseClicked

    private void inprogressPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inprogressPanelMouseClicked
    adminTP.setSelectedIndex(2);
    }//GEN-LAST:event_inprogressPanelMouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
    adminTP.setSelectedIndex(3);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void equipmentTBLMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equipmentTBLMouseReleased

        if (evt.isPopupTrigger()) { 
            int row = equipmentTBL.rowAtPoint(evt.getPoint());
        
            if (row >= 0) {
                equipmentTBL.setRowSelectionInterval(row, row); 
            }

            equipmentPUM.show(equipmentTBL, evt.getX(), evt.getY()); 
        } else {
            System.out.println("Nothing happened!");
        } 
    }//GEN-LAST:event_equipmentTBLMouseReleased

    private void equipmentSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipmentSearchBTNActionPerformed
    String str = equipmentSearchTF.getText();
        
        searchEquipmentTable(str);
    }//GEN-LAST:event_equipmentSearchBTNActionPerformed

    private void equipmentCancelBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipmentCancelBTNActionPerformed
     resetEquipmentTab();
    }//GEN-LAST:event_equipmentCancelBTNActionPerformed

    private void equipmentSaveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipmentSaveBTNActionPerformed
    String name = equipmentNameTF.getText();
    String quantity = equipmentQuantityTF.getText();
    String preferences = equipmentPreferencesTF.getText();

    if (name.equals("") || quantity.equals("") || preferences.equals("")) {
        JOptionPane.showMessageDialog(null, "All fields are required!", "Message", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (equipmentSaveBTN.getText().equals("Save")) {
        int option = JOptionPane.showConfirmDialog(null,
            "This will save the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Equipment equipment = new Equipment(0, name, quantity, preferences);
            if (equipmentDAO.create(equipment)) {
                JOptionPane.showMessageDialog(null, "New equipment has been saved!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Equipment was not saved!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        int option = JOptionPane.showConfirmDialog(null,
            "This will update the entries currently on the form. Are you sure?",
            "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            this.equipment.setName(name);
            this.equipment.setQuantity(quantity);
            this.equipment.setPreferences(preferences);

            if (equipmentDAO.update(this.equipment.getEquipmentSupply_id(), this.equipment)) {
                JOptionPane.showMessageDialog(null, "Equipment has been updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Equipment was not updated!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    resetEquipmentTab();
    }//GEN-LAST:event_equipmentSaveBTNActionPerformed

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
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DashboardP;
    private javax.swing.JPanel InProgressP;
    private javax.swing.JPanel RecordP;
    private javax.swing.JPanel RequestListP;
    private javax.swing.JPanel SupplyListP;
    private javax.swing.JTabbedPane adminTP;
    private javax.swing.JLabel completedCountLBL;
    private javax.swing.JButton consumableCancelTF;
    private javax.swing.JMenuItem consumableDelete;
    private javax.swing.JTextField consumableNameTF;
    private javax.swing.JPanel consumableP;
    private javax.swing.JPopupMenu consumablePUM;
    private javax.swing.JTextField consumablePreferencesTF;
    private javax.swing.JTextField consumableQuantityTF;
    private javax.swing.JButton consumableSaveBTN;
    private javax.swing.JButton consumableSearchBTN;
    private javax.swing.JTextField consumableSearchTF;
    private javax.swing.JTable consumableTBL;
    private javax.swing.JMenuItem consumableUpdate;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JButton equipmentCancelBTN;
    private javax.swing.JMenuItem equipmentDelete;
    private javax.swing.JTextField equipmentNameTF;
    private javax.swing.JPanel equipmentP;
    private javax.swing.JPopupMenu equipmentPUM;
    private javax.swing.JTextField equipmentPreferencesTF;
    private javax.swing.JTextField equipmentQuantityTF;
    private javax.swing.JButton equipmentSaveBTN;
    private javax.swing.JButton equipmentSearchBTN;
    private javax.swing.JTextField equipmentSearchTF;
    private javax.swing.JTable equipmentTBL;
    private javax.swing.JMenuItem equipmentUpdate;
    private javax.swing.JTable inProgressTBL;
    private javax.swing.JLabel inprogressCountLBL;
    private javax.swing.JPanel inprogressPanel;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JMenuItem logoutMI;
    private javax.swing.JButton othersCancelBTN;
    private javax.swing.JMenuItem othersDelete;
    private javax.swing.JTextField othersNameTF;
    private javax.swing.JPanel othersP;
    private javax.swing.JPopupMenu othersPUM;
    private javax.swing.JTextField othersPreferencesTF;
    private javax.swing.JTextField othersQuantityTF;
    private javax.swing.JButton othersSaveBTN;
    private javax.swing.JButton othersSearchBTN;
    private javax.swing.JTextField othersSearchTF;
    private javax.swing.JTable othersTBL;
    private javax.swing.JMenuItem othersUpdate;
    private javax.swing.JLabel pendingCountLBL;
    private javax.swing.JPanel pendingPanel;
    private javax.swing.JLabel pendingstatusLabel;
    private javax.swing.JLabel pendingstatusLabel1;
    private javax.swing.JLabel pendingstatusLabel2;
    private javax.swing.JComboBox<String> priorityCB;
    private javax.swing.JTable recordTBL;
    private javax.swing.JTable requestsTBL;
    private javax.swing.JButton searchBTN;
    private javax.swing.JTextField searchTF;
    private javax.swing.JComboBox<String> statusCB;
    private javax.swing.JButton technologyCancelBTN;
    private javax.swing.JMenuItem technologyDelete;
    private javax.swing.JTextField technologyNameTF;
    private javax.swing.JPanel technologyP;
    private javax.swing.JPopupMenu technologyPUM;
    private javax.swing.JTextField technologyPreferencesTF;
    private javax.swing.JTextField technologyQuantityTF;
    private javax.swing.JButton technologySaveBTN;
    private javax.swing.JButton technologySearchBTN;
    private javax.swing.JTextField technologySearchTF;
    private javax.swing.JTable technologyTBL;
    private javax.swing.JMenuItem technologyUpdate;
    private javax.swing.JComboBox<String> typeCB;
    private javax.swing.JMenuItem userMI;
    private javax.swing.JLabel welcomeLBL;
    // End of variables declaration//GEN-END:variables
}
