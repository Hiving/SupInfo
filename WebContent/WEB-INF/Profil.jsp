<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="skySupApp" ng-controller="FriendsListController">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">
    	var u_sip_id = "${sessionScope.user.sip_id }";
    	var u_sip_password = "${sessionScope.user.sip_password }";
    	var u_sip_server_addr = "163.172.58.60:8089";
    	var skysup_node_addr = "https://localhost:12787";
    </script>
   	<link rel="stylesheet" type="text/css" href="/Supinfo/Css/profile.css" />
    <script src="/Supinfo/SipMl/SIPml.js" ></script>
    <script src="/Supinfo/JavaScript/socket.io.js" ></script>
    <script src="/Supinfo/JavaScript/angular.min.js"></script>
    <script src="/Supinfo/JavaScript/profile.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <jsp:include page="NavBar.jsp" />
    </div>
	
    <div id="income-call-modal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 id="income-call-identity" class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <button type="button" id="income-call-answer" class="btn btn-default btn-block btn-success">Answer</button>
                        </div>
                        <div class="col-md-6">
                            <button type="button" id="income-call-reject" class="btn btn-default btn-block btn-danger">Reject</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
       <div class="col-sm-12">
    		<h1>WELCOME ${sessionScope.user.username }</h1>
    	</div>
	</div>
	<div class="row" ng-repeat="friend in friends">
       <div class="col-sm-12 hidden" style="font-size:30px;" id="call-blinker-{{friend.sip_id}}">
           <span class="blink_me" onclick="return false;">In call with {{friend.username}} !!!</span>
       </div>
    </div>
    <br>
    <script src="https://use.fontawesome.com/45e03a14ce.js"></script>
    <div class="row">
       <div class="col-sm-12">
               <div class="main_section">
        <div class="container">
            <div class="chat_container">
                <div class="col-sm-3 chat_sidebar">
                    <div class="row">
                        <div id="custom-search-input">
                            <div class="input-group col-md-12">
                                <input type="text" class="search-query form-control" placeholder="Friends" />
                            </div>
                        </div>
                        <div class="member_list">
                            <ul class="list-unstyled">
                                <div ng-repeat="friend in friends">
                                    <li class="friend-selector left clearfix" data-target="message-box-{{friend.sip_id}}">
				                     <span class="chat-img pull-left">
				                     <img src="https://www.timeshighereducation.com/sites/default/files/byline_photos/default-avatar.png" alt="User Avatar" class="img-circle">
				                     </span>
                                        <div class="chat-body clearfix">
                                            <div class="header_sec">
                                            	<input type="text" class="hidden" id="identity-sip-id-{{friend.sip_id}}" data-identity-sip-id="{{friend.username}}"/>
                                                <strong class="primary-font">{{friend.username}}</strong> <strong class="pull-right "></strong>
                                            </div>
                                            <div class="contact_sec">
                                                <strong class="primary-font">connected</strong>
                                            </div>
                                        </div>
                                    </li>
                            </ul>
                        </div></div>
                </div>
                <!--chat_sidebar-->
                <div class="col-sm-9">
                    <div ng-repeat="friend in friends">
                        <div class="row hidden friend-message-box" id="message-box-{{friend.sip_id}}">
                            <div class="col-sm-9 ">
                                <div class="row">
                                    <div class="col-sm-12 message_section">
                                        <div class="row">
                                            <div class="chat_area">
                                                <ul class="list-unstyled">
                                                    <li class="left clearfix">
										                     <span class="chat-img1 pull-left">
										                     <img src="https://www.timeshighereducation.com/sites/default/files/byline_photos/default-avatar.png" alt="User Avatar" class="img-circle">
										                     </span>
                                                        <div class="chat-body1 clearfix">
                                                            <p>{{friend.username}} - Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia.</p>
                                                            <div class="chat_time pull-right">09:40PM</div>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div><!--chat_area-->

                                            <div class="message_write">
                                                <textarea class="form-control" id="currentChatMsgChange-{{friend.sip_id}}" ng-keyup="currentChatMsgChange(friend.sip_id)" placeholder="type a message"></textarea>
                                                <div class="clearfix"></div>
                                                <br>
                                                <div class="pull-right">
                                                    <a href="#" ng-click="sendChat(friend.sip_id)" class="pull-right btn btn-success">Send</a>
                                                    <button id="make-call-button-{{friend.sip_id}}" class="btn btn-primary make-call-button" data-sip-target="{{friend.sip_id}}">Make Call</button>
                                                    <a type="button" id="btnHangUp" class="btn btn-danger HangUp-call-button" value="HangUp" data-sip-target="{{friend.sip_id}}">Hang Up</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div> <!--message_section-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
		</div>
    </div>
</div>

<!-- Audios -->
<audio id="audio_remote" autoplay="autoplay"> </audio>
<audio id="ringtone" loop src="https://www.doubango.org/sipml5/sounds/ringtone.wav"> </audio>
<audio id="ringbacktone" loop src="https://www.doubango.org/sipml5/sounds/ringbacktone.wav"> </audio>
<audio id="dtmfTone" src="https://www.doubango.org/sipml5/sounds/dtmf.wav"> </audio>

</body>
</html>