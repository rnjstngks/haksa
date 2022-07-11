import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Student extends JPanel{
	JTextField tfId=null;  
	JTextField tfName=null;
	JTextField tfDepartment=null;
	JTextField tfAddress=null;
	JTextArea taList=null;
	JButton btnSave=null;  // insert -> Create
	JButton btnList=null;  // select -> Read 
	JButton btnModify=null;// update -> Update
	JButton btnRemove=null;// delete -> Delete
	JButton btnSearch=null; // 검색버튼
	
	JMenuItem menuItem1=null; //학생정보
	
	DefaultTableModel model; // 테이블에 들어간느 데이터를 담당
	JTable table=null; // 테이블
	
	public Student() {
		this.setLayout(new FlowLayout());
		
		this.add(new JLabel("학번"));
		this.tfId=new JTextField(13);
		this.add(this.tfId);
		
		this.btnSearch=new JButton("Search");
		this.add(this.btnSearch);
		this.btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","TIGER");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement();
					
					ResultSet rs=stmt.executeQuery("Select * from student where id ='"+tfId.getText()+"'");
					
					model.setRowCount(0); // 목록초기화
					while (rs.next()) {
						String[] row=new String[4];//컬럼의 갯수가 3
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");	
						row[3]=rs.getString("ad"); // 주소추가
						model.addRow(row);
						
						tfId.setText(rs.getString("id")); // setText는 글자를 입력하는 것
						tfName.setText(rs.getString("name"));
						tfDepartment.setText(rs.getString("dept"));
						tfAddress.setText(rs.getString("ad"));
					}
					rs.close(); // 만든것의 역순으로 닫기
					stmt.close();
					conn.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}});
		
		this.add(new JLabel("이름"));		
		this.tfName=new JTextField(20);
		this.add(this.tfName);
		
		this.add(new JLabel("학과"));
		this.tfDepartment=new JTextField(20);
		this.add(this.tfDepartment);
		
		this.add(new JLabel("주소"));
		this.tfAddress=new JTextField(20);
		this.add(this.tfAddress);
		
		// this.taList=new JTextArea(17,23);
		// this.add(new JScrollPane(this.taList));
		
		String colName[]={"학번","이름","학과","주소"};
		model=new DefaultTableModel(colName,0);
		this.table = new JTable(this.model);
		this.table.setPreferredScrollableViewportSize(new Dimension(250,300)); // 테이블크기 250픽셀 X(가로), 300픽셀 Y(세로)
		this.table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				table=(JTable)e.getComponent();//클릭한 테이블 구하기
			    model=(DefaultTableModel)table.getModel();//테이블의 모델 구하기
			    String id=(String)model.getValueAt(table.getSelectedRow(), 0); //학번
			    tfId.setText(id);
			    String name=(String)model.getValueAt(table.getSelectedRow(), 1); //이름
			    tfName.setText(name);
			    String dept=(String)model.getValueAt(table.getSelectedRow(), 2); // 학과 
			    tfDepartment.setText(dept);
			    String ad=(String)model.getValueAt(table.getSelectedRow(), 3); // 주소추가 
			    tfAddress.setText(ad);
			    
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		JScrollPane sp=new JScrollPane(this.table);
		this.add(sp);
		
		this.btnSave=new JButton("등록");
		this.add(this.btnSave);
		this.btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("등록");//디버깅용 메시지
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","TIGER");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement();
					
					stmt.executeUpdate("insert into student values('"+tfId.getText()+"','"+tfName.getText()+"','"+tfDepartment.getText()+"','"+tfAddress.getText()+"')");
					
					ResultSet rs = stmt.executeQuery("select * from student");
					
					model.setRowCount(0); //목록초기화
					while(rs.next()) {
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("ad");
						
						model.addRow(row);
					}
					rs.close();
					stmt.close();
					conn.close();
				}
				catch(Exception e1) {
					e1.printStackTrace();
					
				}
			}});
		this.btnList = new JButton("목록");
		this.btnList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","TIGER");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement();
					
					ResultSet rs = stmt.executeQuery("select * from student");
					
					model.setRowCount(0); //목록초기화
					while(rs.next()) {
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("ad");
						
						model.addRow(row);
					}
					rs.close();
					stmt.close();
					conn.close();
				}
				catch(Exception e1) {
					e1.printStackTrace();
					
				}
			}});
		this.add(this.btnList);
		this.btnModify = new JButton("수정");
		this.btnModify.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","TIGER");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement();
					
					stmt.executeUpdate("update student set name='"+tfName.getText()+"', dept='"+tfDepartment.getText()+"', ad='"+tfAddress.getText()+"' where id ='"+tfId.getText()+"'");

					ResultSet rs = stmt.executeQuery("select * from student");		
					
					model.setRowCount(0); //목록초기화
					while(rs.next()) {
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("ad");
						
						model.addRow(row);
					}
					rs.close();
					stmt.close();
					conn.close();
				}
				catch(Exception e1) {
					e1.printStackTrace();
					
				}
			}});
		this.add(this.btnModify);
		this.btnRemove = new JButton("삭제");
		this.btnRemove.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result=JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "Confrim", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					try {
						//oracle jdbc드라이버 로드
						Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
						//Connection
						Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","TIGER");// 연결
						System.out.println("연결완료");
						
						Statement stmt = conn.createStatement();
						
						stmt.executeUpdate("delete from student where id='"+tfId.getText()+"'");

						ResultSet rs = stmt.executeQuery("select * from student");					
						
						model.setRowCount(0); //목록초기화
						while(rs.next()) {
							String[] row=new String[4];//컬럼의 갯수가 4
							row[0]=rs.getString("id");
							row[1]=rs.getString("name");
							row[2]=rs.getString("dept");
							row[3]=rs.getString("ad");
							model.addRow(row);
							
						}
						rs.close();
						stmt.close();
						conn.close();
					}
					catch(Exception e1) {
						e1.printStackTrace();
						
					}
				}
			}});
		this.add(this.btnRemove);
		
		this.setSize(280, 550);
		this.setVisible(true);
	}
}
