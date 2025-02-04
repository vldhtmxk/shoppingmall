package user.action;

import comm.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.getSession().invalidate(); // 세션 종료
    return "user/jsp/index.jsp";
  }
}
