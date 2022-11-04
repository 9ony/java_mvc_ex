package app;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/*
 * MVC 패턴 ===> 웹 MVC패턴 도입
 * 
 * 모델1방식 : MVC패턴을 적용하지 않을 때
 * 모델2방식 : MVC패턴 적용
 * 
 * MemoApp:=> GUI /Event처리/View 담당 [Presentatioin Layer]
 * XXXDAO: DB접근 로직(비즈니스 로직을 갖는다. CRUD)
 * 		   Data Access Object  [Persistence Layer-영속성 계층]
 * XXXVO/XXXDTO [Domain Layer]
 *  Value Object/ Data Transfer Object
 * 	: 사용자가 입력한 값을 담거나 DB에서 가져온 값을 갖고 있는
 *    객체
 * 
 * */
public class MemoApp extends JFrame {

	JLabel lb;
	JPanel p=new JPanel(new GridLayout(2,1));
	JPanel p_1=new JPanel();
	JPanel p4=new JPanel();
	
	JTextArea ta;
	JTextField tfName, tfDate, tfMsg, tfIdx;
	JButton btAdd, btList, btDel, btEdit,btEditEnd,btFind;
	
	MemoHandler handler;
	MemoFindGui subFrame;
	public MemoApp() {
		super("::MemoApp::");
//		dao=new MemoDAO();
		
		Container cp=this.getContentPane();
		cp.add(p,"North");
		
		ta=new JTextArea("::한줄 메모장::");
		cp.add(new JScrollPane(ta),"Center");
		ta.setEditable(false);//편집 못하도록. 읽기 전용
		
		lb=new JLabel("♥♥한줄 메모장♥♥", JLabel.CENTER);
		p.add(lb);
		lb.setFont(new Font("Serif",Font.BOLD,28));
		p.add(p_1);
		
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		
		
		p_1.setLayout(new GridLayout(2,1));
		p_1.add(p2);
		p_1.add(p3);
		
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		p2.add(new JLabel("작성자: "));
		tfName=new JTextField(12);
		p2.add(tfName);
		
		p2.add(new JLabel("작성일: "));
		tfDate=new JTextField(12);
		p2.add(tfDate);
		
		p2.add(new JLabel("글번호: "));
		tfIdx=new JTextField(12);
		p2.add(tfIdx);
		tfIdx.setEditable(false);
		
		
		p3.add(new JLabel("메모내용: "));
		tfMsg=new JTextField(40);
		p3.add(tfMsg);
		
		btAdd=new JButton("등  록");
		btList=new JButton("글목록");
		p3.add(btAdd);
		p3.add(btList);
		
		String today=getDate();
		tfDate.setText(today);
		tfDate.setEditable(false);
//		tfDate.setText("YY-MM-DD");
		tfDate.setFont(new Font("Dialog",Font.BOLD,14));
		tfDate.setForeground(Color.blue);
		tfDate.setHorizontalAlignment(tfDate.CENTER);
		
		cp.add(p4,"South");
		btDel=new JButton("글삭제");
		btEdit=new JButton("글수정");
		btEditEnd=new JButton("글수정 처리");
		btFind=new JButton("글검색");
		
		p4.add(btDel);
		p4.add(btEdit);
		p4.add(btEditEnd);
		p4.add(btFind);
		
//		subFrame = new MemoFindGui(this);
		handler = new MemoHandler(this);
		subFrame = new MemoFindGui(this);
		
		System.out.println(subFrame);
		btAdd.addActionListener(handler);
		btList.addActionListener(handler);
		btDel.addActionListener(handler);
		btEdit.addActionListener(handler);
		btEditEnd.addActionListener(handler);
		btFind.addActionListener(handler);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,500);
		setVisible(true);
	}//생성자-----------
	
	/**현재 날짜를 YY/MM/DD 포맷의 문자열로 반환하는 메서드*/
	public String getDate() {
		Date today=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd");
								//Java:    yy:년도, MM:월, dd:일, hh:시간, mm:분, ss:초
								//Oracle:  yy:년도, mm:월, dd:일, hh:시간, mi:분, ss:초
		String str=sdf.format(today);
		return str;
	}
	
	public void showMessage(String str) {
		JOptionPane.showMessageDialog(this, str);
	}//----------------------------
	
	public void showTextArea(List<MemoVO> arr) {
		if(arr==null||arr.size()==0) {
			ta.setText("데이터가 없습니다");
		}else {
			ta.setText("");
			ta.append("===================================================\n");
			ta.append("글번호\t작성자\t메모내용\t\t\t\t\t작성일\t\n");
			ta.append("===================================================\n");
			for(MemoVO vo:arr) {
				ta.append(vo.getIdx()+"\t"+vo.getName()+vo.getMsg()+vo.getWdate()+"\n");
			}
			ta.append("===================================================\n");
		}
		ta.setCaretPosition(0);//커서 위치 위로
	}
	public void clearTf() {
		tfIdx.setText("");
		tfName.setText("");
		tfMsg.setText("");
		tfName.requestFocus();
	}
	public static void main(String[] args) {
		new MemoApp();
	}

	public String showInput(String msg) {
		String str = JOptionPane.showInputDialog(msg);
		return str;
	}

	public void setText(MemoVO vo) {
		if(vo==null) return;
		tfIdx.setText(vo.getIdx()+""); //자동형변환
		tfName.setText(vo.getName());
		tfMsg.setText(vo.getMsg());
		tfDate.setText(vo.getWdate()+"");
	}
}





