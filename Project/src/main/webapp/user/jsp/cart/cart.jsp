<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="./user/images/share_musinsa.png">
    <title>무신사</title>

    <%-- Google Font : Gothic A1 --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Gothic+A1&display=swap" rel="stylesheet">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <%-- CSS --%>
    <link rel="stylesheet" type="text/css" href="./user/css/cart/cart.css"/>
</head>
<body>
    <c:choose>
        <c:when test="${not empty sessionScope.customer_info}">
            <%-- header --%>
            <jsp:include page="../layout/header.jsp"></jsp:include>

            <div class="wrap">
                <div class="row">
                    <div class="container">
                        <div class="text-title">
                            <h3>장바구니</h3>
                        </div>
                        <div class="cart-section-container" id="cart-article">
                            <table id="cart-table" class="table">
                                <thead>
                                    <tr>
                                        <th>제품 번호</th>
                                        <th>상품정보</th>
                                        <th>개별 판매가</th>
                                        <th>수량</th>
                                        <th><input type="checkbox" id="cart-all"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${requestScope.cart_list ne null}">
                                        <c:forEach var="item" items="${requestScope.cart_list}" varStatus="st">
                                            <tr data-value="${item.id}">
                                                <td class="order-id">${st.index + 1}</td>
                                                <td class="product-info">
                                                    <div class="prod_info">
                                                        <div class="prod_img">
                                                            <img src="${item.prod_image}"/>
                                                        </div>
                                                        <div class="prod_details">
                                                            <div class="prod_brand">${item.brand}</div>
                                                            <div class="prod_name">${item.p_name}</div>
                                                            <div class="prod_option">
                                                                <div class="prod_option_left">옵션&nbsp;:</div>
                                                                <div class="prod_option_center cart-prod-size">${item.size}</div>
                                                                <div class="prod_option_right">
                                                                    <button type="button" class="btn btn-outline-secondary option-change-btn" data-toggle="modal" data-target="#optionModal">옵션 변경</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td class="product-price">
                                                    <del class="default-price"><fmt:formatNumber value="${item.price}"/>원</del>
                                                    <div class="sale-price"><fmt:formatNumber value="${item.saled_price}"/>원</div>
                                                </td>
                                                <td class="cart-count">
                                                    <button type="button" class="btn btn-secondary btn-minus" onclick="minusCount(this)">-</button>
                                                    <span class="cart-count-value">${item.count}</span>
                                                    <button type="button" class="btn btn-secondary btn-plus" onclick="plusCount(this)">+</button>
                                                </td>
                                                <td>
                                                    <input type="checkbox" name="chk-cart" class="cart-checkbox" data-item-id="${item.id}"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>
                            <div class="cart-notice">
                                <div class="cart-notice-left">
                                    <ol>
                                        <li>무신사 스토어는 기본적으로 대한민국 내 제주도 및 도서 산간 지역 포함 <strong class="cart-notice-emphasize">전 지역, 전 상품
                                            무료배송입니다.</strong></li>
                                        <li>2개 이상의 브랜드를 주문하신 경우, 개별 배송됩니다.</li>
                                        <li>해외 배송 상품이나 일부 업체의 경우, 교환/환불 시 반송료가 다를 수 있으며 상품 페이지에 별도 표기되어 있습니다.</li>
                                        <li>장바구니에는 최대 10개의 상품을 보관할 수 있으며, 주문당 한번에 주문 가능한 상품수는 10개로 제한됩니다.</li>
                                    </ol>
                                </div>
                                <div class="cart-notice-right">
                                    <button type="button" class="btn btn-outline-dark delete-all-btn" onclick="deleteAllCart()">전체 삭제</button>
                                    <button type="button" class="btn btn-outline-dark delete-selected-btn" onclick="deleteCart()">선택 삭제</button>
                                </div>
                            </div>
                            <div class="cart-button">
                                <button type="button" class="btn btn-dark order-btn" onclick="location.href='Controller?type=payment'">주문하기</button>
                            </div>
                        </div>
                    </div>
                </div>

                    <%-- footer --%>
                <jsp:include page="../layout/footer.jsp"></jsp:include>
            </div>

            <%-- modal --%>
            <div class="modal fade" id="optionModal" tabindex="-1" role="dialog" aria-labelledby="optionModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">옵션 변경</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <select class="form-select color-select" id="color">
                                <option>검정</option>
                                <option>네이비</option>
                                <option>아이보리</option>
                            </select>
                            <select class="form-select size-select" id="size">
                                <option>S</option>
                                <option>M</option>
                                <option>L</option>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary cancel-btn" data-dismiss="modal">취소</button>
                            <button type="button" class="btn btn-outline-secondary confirm-btn">변경</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <script>
                window.location.href = "Controller?type=error";
            </script>
        </c:otherwise>
    </c:choose>

    <%-- JQuery --%>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

    <%-- Bootstrap --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

    <script src="./user/js/cart/cart.js"></script>
</body>
</html>
