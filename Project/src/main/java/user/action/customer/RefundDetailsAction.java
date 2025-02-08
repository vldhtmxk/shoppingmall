package user.action.customer;

import user.action.Action;
import user.dao.customer.DeliveryDAO;
import user.dao.customer.OrderDAO;
import user.dao.customer.PointDAO;
import user.vo.customer.CustomerVO;
import user.vo.customer.DeliveryVO;
import user.vo.customer.OrderVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RefundDetailsAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        CustomerVO cvo = (CustomerVO) session.getAttribute("customer_info");

        if (cvo == null) {
            request.setAttribute("session_expired", true);
            return "/user/customer/jsp/error/error.jsp";
        }

        String order_id = request.getParameter("order_id");
        String order_code = request.getParameter("order_code");
        String prod_no = request.getParameter("prod_no");

        OrderVO refund = OrderDAO.selectDetailsByStatus(order_id, cvo.getId(), prod_no, order_code, "7");
        DeliveryVO delivery = DeliveryDAO.selectRetrieveInfo(order_id);
        List<OrderVO> coupon = OrderDAO.selectOrderCoupons(order_id, cvo.getId(), order_code, prod_no);
        int point_amount = PointDAO.selectPointAmount(cvo.getId(), order_code);
        OrderVO vo = OrderDAO.selectSellerAddress(order_id);

        request.setAttribute("refund", refund);
        request.setAttribute("delivery", delivery);
        request.setAttribute("coupon", coupon);
        request.setAttribute("point_amount", point_amount);
        request.setAttribute("vo", vo);

        return "/user/customer/jsp/mypage/refundDetails.jsp";
    }
}
