package user.action.customer;

import user.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExchangeAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/user/customer/jsp/mypage/exchange.jsp";
    }
}
