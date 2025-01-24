package user.action;

import user.dao.DeliveryDAO;
import user.dao.LogDAO;
import user.vo.CustomerVO;
import user.vo.DeliveryVO;
import user.vo.LogVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeliveryAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        CustomerVO cvo = (CustomerVO) session.getAttribute("customer_info");

        String viewPage = null;
        if (action != null) {
            switch (action) {
                case "select":
                    // 배송지 조회
                    List<DeliveryVO> d_list = DeliveryDAO.selectDelivery(cvo.getId());
                    request.setAttribute("d_list", d_list);

                    viewPage = "/user/jsp/mypage/components/delivery.jsp";
                    break;
                case "insert":
                    String i_name = request.getParameter("name");
                    String i_phone = request.getParameter("phone");
                    String i_pos_code = request.getParameter("pos_code");
                    String i_addr1 = request.getParameter("addr1");
                    String i_addr2 = request.getParameter("addr2");
                    String i_deli_request = request.getParameter("deli_request");
                    String i_chkDefault = request.getParameter("chkDefault");

                    // 모든 배송지의 is_default를 false로 설정
                    if (i_chkDefault.equals("1")) {
                        DeliveryDAO.updateIsDefault(cvo.getId());
                    }

                    // 배송지 추가
                    int i_cnt = DeliveryDAO.insertDelivery(cvo.getId(), i_name, i_phone, i_pos_code, i_addr1, i_addr2, i_chkDefault, i_deli_request);

                    if (i_cnt > 0) {
                        // 추가 로그
                        LogVO lvo = new LogVO();
                        StringBuffer sb = new StringBuffer();
                        lvo.setCus_no(cvo.getId());
                        lvo.setTarget("delivery");
                        sb.append("name : " + i_name + ", ");
                        sb.append("phone : " + i_phone + ", ");
                        sb.append("pos_code : " + i_pos_code + ", ");
                        sb.append("addr1 : " + i_addr1 + ", ");
                        sb.append("addr2 : " + i_addr2 + ", ");
                        sb.append("request : " + i_deli_request + ", ");
                        sb.append("is_default : " + i_chkDefault);
                        lvo.setCurrent(sb.toString());
                        LogDAO.insertLog(lvo);
                    }

                    viewPage = "/user/jsp/mypage/mypage.jsp";
                    break;
                case "update":
                    String u_id = request.getParameter("id");
                    String u_name = request.getParameter("name");
                    String u_phone = request.getParameter("phone");
                    String u_pos_code = request.getParameter("pos_code");
                    String u_addr1 = request.getParameter("addr1");
                    String u_addr2 = request.getParameter("addr2");
                    String u_deli_request = request.getParameter("deli_request");
                    String u_chkDefault = request.getParameter("chkDefault");

                    // 모든 배송지의 is_default를 false로 설정
                    if (u_chkDefault.equals("1")) {
                        DeliveryDAO.updateIsDefault(cvo.getId());
                    }

                    // 배송지 수정
                    int u_cnt = DeliveryDAO.updateDelivery(u_id, u_name, u_phone, u_pos_code, u_addr1, u_addr2, u_chkDefault, u_deli_request);

                    if (u_cnt > 0) {
                        DeliveryVO d_vo = DeliveryDAO.selectDeliveryById(u_id);

                        // 수정 로그
                        LogVO lvo = new LogVO();
                        StringBuffer sb = new StringBuffer();
                        lvo.setCus_no(cvo.getId());
                        lvo.setTarget("delivery");
                        sb.append("id : " + d_vo.getId() + ", ");
                        sb.append("name : " + d_vo.getName() + ", ");
                        sb.append("phone : " + d_vo.getPhone() + ", ");
                        sb.append("pos_code : " + d_vo.getPos_code() + ", ");
                        sb.append("addr1 : " + d_vo.getAddr1() + ", ");
                        sb.append("addr2 : " + d_vo.getAddr2() + ", ");
                        sb.append("request : " + d_vo.getRequest() + ", ");
                        sb.append("is_default : " + d_vo.getIs_default());
                        lvo.setPrev(sb.toString());
                        sb = new StringBuffer();
                        sb.append("id : " + u_id + ", ");
                        sb.append("name : " + u_name + ", ");
                        sb.append("phone : " + u_phone + ", ");
                        sb.append("pos_code : " + u_pos_code + ", ");
                        sb.append("addr1 : " + u_addr1 + ", ");
                        sb.append("addr2 : " + u_addr2 + ", ");
                        sb.append("request : " + u_deli_request + ", ");
                        sb.append("is_default : " + u_chkDefault);
                        lvo.setCurrent(sb.toString());
                        LogDAO.updateLog(lvo);
                    }

                    viewPage = "/user/jsp/mypage/mypage.jsp";
                    break;
                case "delete":
                    String d_id = request.getParameter("id");

                    // 배송지 삭제
                    int d_cnt = DeliveryDAO.deleteDelivery(d_id);

                    if (d_cnt > 0) {
                        // 삭제 로그
                        LogVO lvo = new LogVO();
                        StringBuffer sb = new StringBuffer();
                        lvo.setCus_no(cvo.getId());
                        lvo.setTarget("delivery");
                        sb.append("id : " + d_id + "\n");
                        lvo.setPrev(sb.toString());
                        LogDAO.deleteLog(lvo);
                    }

                    viewPage = "/user/jsp/mypage/components/delivery.jsp";
                    break;
            }
        } else {
            viewPage = "/user/jsp/mypage/components/delivery.jsp";
        }

        return viewPage;
    }
}