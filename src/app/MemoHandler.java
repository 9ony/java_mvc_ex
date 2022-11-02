package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemoHandler implements ActionListener{
	
	private MemoApp app;
	public MemoHandler(MemoApp app) {
		this.app=app;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o==app.btAdd) {
			addMemo();
		}
	}
	public void addMemo() {
		//tfName, tfMsg에 입력한 값 받아오기
		
		// 유효성 체크
		
		//받아온 값을 MomoVO객체에 담아준다
		
		//dao의 insertMemo(vo)호출하고 그 결과값을 받아온다
		
		//그 결과값에 따른 메세지 처리
	}
}
