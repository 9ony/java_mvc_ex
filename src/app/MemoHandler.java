package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
			addMemo();
		}
	}
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
			}
			// 그 결과값에 따른 메세지 처리
			if(result==1) {
				app.showMessage("메모등록 완료!");
			}else {
				app.showMessage("메모등록 실패!");
			}
		}
	}
}
