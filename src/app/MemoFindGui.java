package app;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemoFindGui extends JFrame {
	DefaultTableModel model;
	String[] colHeader= {"글번호","글내용","작성자","작성일"};
	
	public MemoFindGui(MemoApp sub) {
        initComponents();//gui 구성
        //리스너 부착-----------------
        btFindEnd.addActionListener(sub.handler);
        tfKeyword.addActionListener(sub.handler);
    }//---------------------------------
	//JTable컴포넌트
	/*swing-JFC
	 * MVC패턴
	 * Model - VO/DAO, XXXModel ==> 데이터를 가지고 있는 계층
	 * View - JTextArea, JButton, JTable, JList....
	 * Controller - XXXRender, XXXEditor....
	 * 
	 * JTable : 행과 열로 구성된 컴포넌트 ==>View
	 * DefaultTableModel : => Model => db에서 가져온 데이터를 모델에 넘겨준다
	 * View와 Model을 연결하는 작업을 해야 서로 연결된다.
	 * */
	public void showTable(List<MemoVO> arr) {
		Object[][] data=new Object[arr.size()][this.colHeader.length];
		//반복문 돌면서 arr에 담긴 MemoVO를 꺼내서 data로 옮긴다
		for(int i=0;i<data.length;i++) {
			MemoVO vo=arr.get(i);
			data[i][0]=vo.getIdx();
			data[i][1]=vo.getMsg();
			data[i][2]=vo.getName();
			data[i][3]=vo.getWdate();
		}//for---------------
		//data==> 데이터 ==> 모델이 갖는다
		model=new DefaultTableModel(data, colHeader);
		//View와 Model을 연결해야 데이터가 화면계층에 표현된다.
		table.setModel(model);
	}//----------------------------------------
    private void initComponents() {
		setTitle("::한줄 메모장 검색::");
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jPanel1 = new JPanel();
        comboType = new JComboBox<>();
        btFindEnd = new JButton();
        tfKeyword = new JTextField();
        jScrollPane1 = new JScrollPane();
        table = new JTable();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);//프로세스 종료
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//팝업창만 닫기

        jLabel1.setFont(new java.awt.Font("휴먼편지체", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("한줄 메모장 글 검색");

        jLabel2.setIcon(new ImageIcon(getClass().getResource("/app/memo2.jpg"))); // NOI18N
        jLabel2.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "작성자", "글내용", " " }));

        btFindEnd.setBackground(new java.awt.Color(0, 0, 153));
        btFindEnd.setForeground(new java.awt.Color(255, 255, 255));
        btFindEnd.setText("검색");
       

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btFindEnd)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btFindEnd)
                    .addComponent(tfKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btFindEnd, comboType, tfKeyword});

        table.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "글번호", "글내용", "작성자", "작성일"
            }
        ));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );

        pack();
    }// </editor-fold>//
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton btFindEnd;
    javax.swing.JComboBox<String> comboType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    javax.swing.JTable table;//View
    javax.swing.JTextField tfKeyword;

	
}

