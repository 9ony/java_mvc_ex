package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MemoHandler implements ActionListener {

	private MemoApp app;
	private MemoDAO mdao = new MemoDAO();

	public MemoHandler(MemoApp app) {
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == app.btAdd) {
			//app.setTitle("###"); //버튼작동확인용
			addMemo();
		}
		else if(o == app.btList) {
			listMemo();
		}else if (o == app.btDel) {
			deleteMemo();
		}else if (o ==app.btEdit) {
			editMemo();
		}else if (o == app.btEditEnd) {
			editMemoEnd();
		}else if(o==app.btFind) {
			app.subFrame.setLocation(app.getWidth()-5,0);//x,y
			app.subFrame.setVisible(true);
			System.out.println(app.subFrame);
		}else if(o==app.subFrame.btFindEnd || o==app.subFrame.tfKeyword ) {
			app.setTitle("SEARCH TEST"); //버튼작동확인용
			findMemo();
		}
	}

	private void editMemo() {
		String idxStr=app.showInput("수정할 글번호를 입력하세요");
		if(idxStr==null) return;
		//글번호(PK)
		try {
		MemoVO vo=mdao.selectMemo(Integer.parseInt(idxStr.trim()));
		if(vo==null) {
			app.showMessage(idxStr+"번 글은 존재하지 않아요");
			return;
		}
		app.setText(vo);
		}catch(SQLException e) {
			app.showMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	private void editMemoEnd() {
		//1.사용자가 입력한 값 얻기
		String idxstr = app.tfIdx.getText();
		String name =app.tfName.getText();
		String msg = app.tfMsg.getText();
		//2.유효성
		if(idxstr.isEmpty()||name.isEmpty()) {
			app.showMessage("글번호와,작성자는 반드시 입력해야 해요");
			return;
		}
		int idx=Integer.parseInt(idxstr.trim());
		
		MemoVO vo=new MemoVO(idx,name,msg,null);
		try {
			int n = mdao.updateMemo(vo);
			String str=(n>0)?"글 수정 성공":"글 수정 실패";
			app.showMessage(str);
		}catch(SQLException e) {
			e.printStackTrace();
			app.showMessage(e.getMessage());
		}
		
	}
	//메모추가기능
	public void addMemo() {
		// tfName, tfMsg에 입력한 값 받아오기
		String name = app.tfName.getText();
		String msg = app.tfMsg.getText();
		// 유효성 체크
		String err = "";
		if (name.equals("")) {
			app.showMessage("이름을 입력하세요");
			app.tfName.requestFocus();
		} else if (msg.equals("")) {
			app.showMessage("메세지를 입력하세요");
			app.tfMsg.requestFocus();
		} else {
			// 받아온 값을 MomoVO객체에 담아준다
			MemoVO mv = new MemoVO(1, name, msg, null);
			int result = 0;
			// dao의 insertMemo(vo)호출하고 그 결과값을 받아온다
			try {
				result = mdao.insertMemo(mv);
			} catch (SQLException e) {
				e.printStackTrace();
				// 그 결과값에 따른 메세지 처리
				app.showMessage("메모등록 실패!\n"+e.getMessage());
			}
//			// 그 결과값에 따른 메세지 처리
//			if(result==1) {
//				app.showMessage("메모등록 완료!");
//				listMemo();
//			}else {
//				app.showMessage("메모등록 실패!\n");
//			}
		}
	}
	//메모삭제기능
	public void deleteMemo() {
		String idxStr=app.showInput("삭제할 글번호를 입력해주세요");
		if(idxStr==null) return;
		try {
		int n=mdao.deleteMemo(Integer.parseInt(idxStr.trim()));
		String str=(n>0)?"글 삭제 성공" : "글 삭제 실패";
		app.showMessage(str);
		}catch(SQLException e) {
			e.printStackTrace();
			app.showMessage(e.getMessage());
		}
	}
	//메모목록반환기능
	public void listMemo() {
		try {
		List<MemoVO> arr = mdao.selectMemoAll();
		app.setTitle("글 개수:"+arr.size()); //
		app.showTextArea(arr);
		}catch(SQLException e) {
			e.printStackTrace();
			app.showMessage(e.getMessage());
		}
	}
	public void findMemo() {
		//검색 유형 얻어오기
		int type=app.subFrame.comboType.getSelectedIndex();
		System.out.println("type: "+type);//
		//검색어 얻어오기
		String keyword=app.subFrame.tfKeyword.getText();
		if(keyword==null||keyword.trim().isBlank()) {
			app.showMessage("검색할 키워드를 입력하세요");
			app.subFrame.tfKeyword.requestFocus();
			return;
		}
		try {
			List<MemoVO> arr=mdao.findMemo(type, keyword);
			if(arr==null||arr.size()==0) {
				app.showMessage("검색한 글이 없어요");
				return;
			}
			//app.showTextArea(arr);
			app.subFrame.showTable(arr);
		}catch(SQLException  e) {
			e.printStackTrace();
			app.showMessage(e.getMessage());
		}
	}
}
