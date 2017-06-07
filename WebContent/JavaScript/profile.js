   var myApp = angular.module("skySupApp", []);
   
    (function(app){
    	  "use strict";
    	  app.controller("FriendsListController", function($scope, $http){
              var sipStack;
              var callSession;
              var registerSession;
              var publishSession;
              var oSipSessionCall = false;
              var oConfigCall;
              var oCurrentBlinker = null;
              var currentEmittingCall = null;

              function startRingTone() {
                  try { ringtone.play(); }
                  catch (e) { }
              }

              function stopRingTone() {
                  try { ringtone.pause(); }
                  catch (e) { }
              }

              function startRingbackTone() {
                  try { ringbacktone.play(); }
                  catch (e) { }
              }

              function stopRingbackTone() {
                  try { ringbacktone.pause(); }
                  catch (e) { }
              }

              var acceptCall = function(e){
                  e.newSession.accept(); // e.newSession.reject() to reject the call
              }
              


              function onCallTerminate(e) {
                  oSipSessionCall = false;
                  stopRingTone();
                  
                  if (oCurrentBlinker != null) {
                	  oCurrentBlinker.addClass('hidden');
                  }

                  if (e.description == "Request Cancelled") {
                      $('#income-call-modal').modal('hide');
                  }
              }

              function reciveCall(eventAnswer) {
                  if (oSipSessionCall) {
                      // do not accept the incoming call if we're already 'in call'
                      eventAnswer.newSession.hangup(); // comment this line for multi-line support
                  } else {
                      oSipSessionCall = eventAnswer.newSession;
                      oSipSessionCall.setConfiguration(oConfigCall);

                      startRingTone();
                      var sRemoteNumber = (oSipSessionCall.getRemoteFriendlyName() || 'unknown');
                      console.log("<i>Incoming call from [<b>" + sRemoteNumber + "</b>]</i>");

                      $('#income-call-identity').text("New call comming from '" + $('#identity-sip-id-'+sRemoteNumber).data('identity-sip-id') + "'");
                      var $blinker = $('#call-blinker-'+sRemoteNumber);
                      $('#income-call-answer').click(function () {
                          eventAnswer.newSession.accept();
                          $blinker.removeClass('hidden');
                          oCurrentBlinker = $blinker;

                          stopRingTone();
                          $('#income-call-modal').modal('hide');

                          var target = 'message-box-' + sRemoteNumber;
                          $('.friend-selector').removeClass('user-selected-li');
                          $('.friend-selector').each(function () {
                              var mptarget = $(this).data('target');
                              if (mptarget == target) {
                                  $(this).addClass('user-selected-li');
                                  $('.friend-message-box').each(function () {
                                      if ($(this).attr('id') == mptarget) {
                                          $(this).removeClass('hidden');
                                      } else {
                                          $(this).addClass('hidden');
                                      }
                                  });
                              }
                          });

                          $('.make-call-button').attr('disabled', 'disabled');

                      });

                      $('#income-call-reject').click(function () {
                          eventAnswer.newSession.reject();
                          stopRingTone();
                          oSipSessionCall = false;
                          $('#income-call-modal').modal('hide');
                      });

                      $('#income-call-modal').modal('show');
                      //e.newSession.accept();
                      //stopRingTone();
                      //sipCall('call-audio');
                  }
              }

              var onDuringCallReceptionEventSession = function(e){
                  console.info('**** session XXXXRECEIVINGXXXX event = ' + e.type);

                  // Call ends or canceled
                  if(e.type == 'terminated'){
                      onCallTerminate(e);
                  }
              };

              var onDuringCallEmissionEventSession = function(e){
                  console.info('**** session XXXXEMISSIONXXXX event = ' + e.type);
                  console.info(e);


                  if(e.type == 'connected' && e.description == "In Call" && currentEmittingCall){
                      $('.make-call-button').attr('disabled', 'disabled');
                      stopRingbackTone();
                      oCurrentBlinker = $('#call-blinker-'+currentEmittingCall.targetID);
                      oCurrentBlinker.removeClass('hidden');
                  }
                  
                  if(e.type == 'i_ao_request' && e.description == "Trying"){
                      $('.make-call-button').attr('disabled', 'disabled');
                  }
                  
                  if(e.type == 'i_ao_request' && e.description == "Ringing"){
                      $('.make-call-button').attr('disabled', 'disabled');
                      startRingbackTone();
                      oCurrentBlinker = $('#call-blinker-'+currentEmittingCall.targetID);
                      oCurrentBlinker.removeClass('hidden');
                  }

                  if(e.type == 'terminated' && e.description == "Busy Here"){
                      $('.make-call-button').removeAttr('disabled');
                      stopRingbackTone();
                      currentEmittingCall = oSipSessionCall = null;
                      onCallTerminate(e);
                      alert("The user is busy!!!");
                  }

                  if(e.type == 'terminated' && e.description == "Not Found"){
                      $('.make-call-button').removeAttr('disabled');
                      stopRingbackTone();
                      currentEmittingCall = oSipSessionCall = null;
                      alert("The user does not exists!!!");
                  }
                  
                  if(e.type == 'terminated' && e.description == "Request Terminated"){
                      $('.make-call-button').removeAttr('disabled');
                      stopRingbackTone();
                      currentEmittingCall = oSipSessionCall = null;
                  }
                  

                  if(e.type == 'terminated' && e.description == "Call terminated"){
                      $('.make-call-button').removeAttr('disabled');
                      if (currentEmittingCall != null) {
                    	  oCurrentBlinker = $('#call-blinker-'+currentEmittingCall.targetID);
                          oCurrentBlinker.addClass('hidden');
                      }
                      
                      oCurrentBlinker = currentEmittingCall = oSipSessionCall = null;
                  }
                  
                  if(e.type == 'terminated' && e.description == "Service Unavailable"){
                      $('.make-call-button').removeAttr('disabled');
                      oCurrentBlinker = currentEmittingCall = oSipSessionCall = null;
                      alert("The user is not connected!!!");
                  }
              };

              oConfigCall = {
                  audio_remote: document.getElementById("audio_remote"),
                  events_listener: { events: '*', listener: onDuringCallReceptionEventSession },
                  sip_caps: [
                      { name: '+g.oma.sip-im' },
                      { name: 'language', value: '\"en,fr\"' }
                  ]
              };

              function sipHangUp() {
                  if (oSipSessionCall) { oSipSessionCall.hangup(); }
                  if (callSession) { callSession.hangup(); }
                  
                  if (currentEmittingCall) {
                      oCurrentBlinker = $('#call-blinker-'+currentEmittingCall.targetID);
                      oCurrentBlinker.addClass('hidden');
                  }

                  $('.make-call-button').removeAttr('disabled');
                  stopRingbackTone();
                  currentEmittingCall = currentEmittingCall = oSipSessionCall = null;
              }

              function sipCall(target) {
                  if (null == currentEmittingCall && sipStack && !oSipSessionCall) {
                      callSession = sipStack.newSession('call-audio', {
                          audio_remote: document.getElementById('audio_remote'),
                          events_listener: { events: '*', listener: onDuringCallEmissionEventSession } // optional: '*' means all events
                      });

                      console.log('<i>Calling user = '+target+'</i>');
                      currentEmittingCall = {
                          targetID : String(target)
                      };

                      callSession.call(String(target));
                  } else {
                      alert('<i>FAILED - USER ALREADY IN CALL</i>');
                  }
              }

              var eventsListener = function(e){
                  console.info('**** session event = ' + e.type);
                  if(e.type == 'started'){
                      registerSession = sipStack.newSession('register', {
                          events_listener: { events: '*', listener: eventsListener } // optional: '*' means all events
                      });
                      registerSession.register();
                  }

                  if(e.type == 'i_new_call'){
                      reciveCall(e);
                  }

                  if(e.type == 'i_notify'){
                      console.info('NOTIFY content = ' + e.getContentString());
                      console.info('NOTIFY content-type = ' + e.getContentType());

                      if (e.getContentType() == 'application/pidf+xml') {
                          if (window.DOMParser) {
                              var parser = new DOMParser();
                              var xmlDoc = parser ? parser.parseFromString(e.getContentString(), "text/xml") : null;
                              var presenceNode = xmlDoc ? xmlDoc.getElementsByTagName ("presence")[0] : null;
                              if(presenceNode){
                                  var entityUri = presenceNode.getAttribute ("entity");
                                  var tupleNode = presenceNode.getElementsByTagName ("tuple")[0];
                                  if(entityUri && tupleNode){
                                      var statusNode = tupleNode.getElementsByTagName ("status")[0];
                                      if(statusNode){
                                          var basicNode = statusNode.getElementsByTagName ("basic")[0];
                                          if(basicNode){
                                              console.info('Presence notification: Uri = ' + entityUri + ' status = ' + basicNode.textContent);
                                          }
                                      }
                                  }
                              }
                          }
                      }
                  }
              }
              
              SIPml.init(
                      function (e) {
                          sipStack = new SIPml.Stack({
                              realm: 'skysup.chakib-nabil.online',
                              impi: u_sip_id,
                              impu: 'sip:'+u_sip_id+'@skysup.chakib-nabil.online',
                              password: u_sip_password,
                              display_name: '',
                              websocket_proxy_url: 'wss://' + u_sip_server_addr + '/ws',
                              outbound_proxy_url: null,
                              ice_servers: "[{'url': 'stun:stun.l.google.com:19302' }]",
                              enable_rtcweb_breaker: true,
                              enable_early_ims: false, // Must be true unless you're using a real IMS network
                              enable_media_stream_cache: false,
                              sip_headers: [
                                  { name: 'User-Agent', value: 'IM-client/OMA1.0 sipML5-v1.2016.03.04' },
                                  { name: 'Organization', value: 'SkySup' }
                              ],
                              events_listener: {
                                  events: '*',
                                  listener: eventsListener
                              }
                          });

                          sipStack.start();
                      }
              );

              function init_binding () {
            	  (function blink() {
                      $('.blink_me').fadeOut(1000).fadeIn(1000, blink);
                  })();

            	  $('.friend-selector').each(function () {
            		  if (!$(this).hasClass('ele-binded')) {
            			  $(this).addClass('ele-binded');
            			  $(this).click(function () {
  	            			var target = $(this).data('target');
  	      	                $('.friend-selector').removeClass('user-selected-li');
  	      	                $(this).addClass('user-selected-li');
  	
  	      	                $('.friend-message-box').each(function () {
  	      	                    if ($(this).attr('id') == target) {
  	      	                        $(this).removeClass('hidden');
  	      	                    } else {
  	      	                        $(this).addClass('hidden');
  	      	                    }
  	      	                });   
            			  });
          		  		}
            	  });
            	  
            	  $('.make-call-button').each(function () {
            		  if (!$(this).hasClass('ele-binded')) {
            			  $(this).addClass('ele-binded');
            			  
            			  $(this).click(function () {
            				  var target = $(this).data('sip-target');
                              sipCall(target);
						  });
                	  }
            	  });

            	  $('.HangUp-call-button').each(function () {
            		  if (!$(this).hasClass('ele-binded')) {
                		  $(this).addClass('ele-binded');
                		  
                		  $(this).click(function () {
                			  var target = $(this).data('sip-target');
                              sipHangUp(target);
                              return false;
						  });
                	  }
            	  });
              }
              
              function isFriendExists (sip_id) {
            	  var alreadyExists = false;
    			  $scope.managedFriends.forEach(function (ele) {
    				  if (sip_id == element.sip_id) {
    					  return true;
    				  }
				  });
    			  
    			  return alreadyExists;
              }
              
              function refreshFriends () {
            	  $http.get("/Supinfo/apiClassForUsersConnected").then(function(response) {
            		  response.data.User.forEach(function(element) {
            			  var alreadyExists = false;
            			  $scope.friends.forEach(function (ele) {
            				  if (ele.sip_id == element.sip_id) {
            					  alreadyExists = true;
            					  return false;
            				  }
						  });
            			  
            			  if (alreadyExists == false) {
            				  $scope.friends.push(element);
            			  }
            	   	  });
            		  
           		  	  // remove disconnected users
            		  $scope.friends.forEach(function(element) {
            			  var alreadyExists = false;
            			  response.data.User.forEach(function (ele) {
            				  if (ele.sip_id == element.sip_id) {
            					  alreadyExists = true;
            					  return false;
            				  }
						  });
            			  
            			  if (alreadyExists == false) {
            				  $scope.friends = $.grep($scope.friends, function(value) {
            					  return value != element;
            				 });
            			  }
            	   	  	});
            		  
            		  $(function() { init_binding(); });
            	  });
              };
              
              $scope.managedFriends = [];
              $scope.friends = [];
              $scope.currentChatMsgs = [];
              
              // chat sockets
              var chatSocket = io(skysup_node_addr, {secure: true});
              
              $scope.sendChat = function(reciever) {
            	  chatSocket.emit('newChatMessage', reciever, $scope.currentChatMsgs);
              };
              
              
              $scope.currentChatMsgChange = function(sip_id) {
            	  $scope.currentChatMsgs[sip_id] = $('#currentChatMsgChange-'+sip_id).val();
            	  console.log($scope.currentChatMsgs);
              };
              
              angular.element(document).ready(function () {
            	  refreshFriends();
                  setInterval(function(){ refreshFriends(); }, 3000);
   			  });
    	  });
    	})(myApp);