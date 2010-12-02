/**
* File: app.js
* <p/>
* Copyright (c) 2010 RENO NATALINO,
* All Rights Reserved.
* <p/>
* This software is the confidential and proprietary information
* of RENO NATALINO ("Confidential Information"). You shall not
* disclose such Confidential Information and shall use it only in
* accordance with the terms of the license agreement you entered
* into with RENO NATALINO.
* <p/>
* Project:	sisy
* Author : Reno Natalino
* Created: Sep 14, 2010, 5:39:03 PM
*/

/* Global Javascript Utilities for Sisy Application */

// Global namespace
jQuery.namespace("G");

// Main
G.fn = function($){
    
    var ctxPath;
    var lastButtonClicked = '';

    var initEvents = function() {
        // register click event for buttons to set lastButtonClicked
        $('#ui-container-form').delegate('click.regclick', '.button', function(e) {
            lastButtonClicked = e.target.id;
        });
    };
    
    /**
     * <p>Initialize a dialog box for confirmation, the content will be supplied by the result DOM of the selector,
     * button Ok function will be set by <code>yes</code> property of the option, and Cancel function wil be set by <code>no</code> property.</p>
     *
     * @param {String} selector the selector of the dialog contents
     * @param {Object} options the dialog box options
     */
    var initConfirm = function(selector, options) {
        $(selector).dialog($.extend({
            closeOnYes: true,
            closeOnNo: true,
            submit: true,
            title: 'Sisy Application',
            validate: function() {
                return true;
            },
            yes: function() {},
            no: function() {},
            button: '',
            bgiframe: true,
            modal: true,
            resizable: false,
            minHeight: 125,
            buttons: {
                No: function() {
                    var opts = {
                        no: $(this).dialog('option', 'no'),
                        closeOnNo: $(this).dialog('option', 'closeOnNo')
                    };

                    opts.no(this.id);
                    if (opts.closeOnNo) {
                        $(this).dialog('destroy');
                        $(this).remove();
                    }
                },
                Yes: function() {
                    var opts = {
                        submit: $(this).dialog('option', 'submit'),
                        validate: $(this).dialog('option', 'validate'),
                        yes: $(this).dialog('option', 'yes'),
                        button: $(this).dialog('option', 'button'),
                        closeOnYes: $(this).dialog('options', 'closeOnYes')
                    };

                    if (opts.validate()) {
                        opts.yes(this.id);
                        var form = $('input[name='+opts.button+']').parents('form');
                        if (form.length > 0 && opts.submit) {
                            form[0].action += form[0].action.indexOf('?') !== -1 ? '&' : '?';
                            form[0].action += '_eventName='+opts.button;
                            form[0].submit();
                        } else if (opts.closeOnYes) {
                            $(this).dialog('close');
                        }
                    } else {
                        $(this).dialog('close');
                    }
                }
            },
            close: function() {
                $(this).dialog('destroy');
                $(this).remove();
            },
            open: function() {
                $('.ui-dialog-titlebar-close.ui-corner-all').remove();
                $('.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix').remove();

            },
            autoOpen: true
        }, options || {}));
    };
    /**
     * <p>Generate a random string of alphanumeric</p>
     *
     * @param {int} length the lenght of the generated string default will be 8
     */
    var generateRandomString = function(length) {
        var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
        var randomstring = '';
        for (var i=0; i<(length || 8); i++) {
            var rnum = Math.floor(Math.random() * chars.length);
            randomstring += chars.substring(rnum,rnum+1);
        }
        return randomstring;
    };
    var addingClassButton = function(){
        $('.ui-sisy-button').addClass('ui-state-default');
        $('.ui-sisy-button').hover(function(){
            $(this).addClass("ui-state-hover");
        },function(){
            $(this).removeClass("ui-state-hover");
        });
    };
    return {
        init: function(){
            // init add class when hover to button with class .ui-sisy-button
            addingClassButton();
            // init pre-defined events
            initEvents();
            // init pre-defined to clear .png see jquery.pngFix.js 
            $j(document).pngFix();
        },
        // interface: G.fn.clearMessage, interface for clear meesage
        clearMessage: function(){
            $('.messages.ui-state-highlight').fadeOut('slow');
            $('.messages.ui-state-highlight').remove();
        },
        // interface: G.fn.clearError, interface for clear error message
        clearError: function(){
            $('.errors.ui-state-error').fadeOut('slow');
            $('.errors.ui-state-error').remove();
            $('.error').removeClass('error');
        },
        // interface: G.fn.addMessage, interface for add message
        addMessage: function(msg, append){
            // clear the error first
            G.fn.clearError();
            // template for message            
            var _wrapper = '<div class="ui-state-highlight" style="display:none; font-family: Verdana,Arial,sans-serif !important; font-size: 1.1em !important; padding: 5pt 0.7em !important;">#{messages}</div>';
            // message template
            var _message = '<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><strong>Info : </strong>#{messages}</p>';
            // default message
            var message = '';
            // init default message 
            message = $.tmpl(_message,{ 'messages': msg });
            // animate
            $('.ui-state-highlight').fadeIn(1500);
        },
        // interface: G.fn.simpleModal, interface for modal dialog development, note: with button close (x).
        simpleModalClose: function(selector){
            var modelTmpl = {
                init: function(){
                    $j(selector).modal({
                        modal:true,
                        closeHTML: "<a href='#' title='Close' class='modal-close'>x</a>",
                        autoResize: false,
                        overlayClose: true,
                        escClose: true,
                        close: true,
                        onOpen: modelTmpl.open,
                        onClose: modelTmpl.close                        
                    });
                },
                open: function(dialog){
                    $j(selector).show();
                    dialog.overlay.fadeIn('slow',function(){
                        dialog.container.fadeIn('slow',function(){
                            dialog.data.slideDown('slow');
                        });
                    });
                },
                close: function(dialog){
                    dialog.data.fadeOut('slow', function(){
                        dialog.container.hide('slow', function(){
                            dialog.overlay.slideUp('slow', function(){
                                $.modal.close();
                            });
                        });
                    });
                }
            };
            modelTmpl.init();
        },
        // interface: G.fn.confirm, Generic confirmation dialog call
        confirm: function(options) {
        	var selector = options.selector;
            if (!options.selector) {
                // generate random id for DOM
                var id = 'dialog-' + generateRandomString();
                selector = '#' + id;
                // dialog DOM template
                var _tpl = '<!-- Generated dialog-box ##{id} --><div id="#{id}"><p style="text-align:left !important;" class="confirm-msg"><b>#{msg}</b></p></div>';
                // append DOM into page
                $j('.ui-page-container').append($j.tmpl(_tpl, {'id':id, msg:options.msg}));
            }

            $j(selector).dialog($j.extend({
                closeOnYes: true,
                closeOnNo: true,
                submit: true,
                title: '',
                closeOnEscape: true,
                draggable: false,
                validate: function() {
                    return true;
                },
                yes: function() {
                },
                no: function() {
                },
                button: '',
                bgiframe: true,
                modal: true,
                resizable: false,
                minHeight: 125,
                overlay: {
                    backgroundColor: '#000',
                    opacity: 0.5
                },
                buttons: {
                    No: function() {
                        var opts = {
                            no: $j(this).dialog('option', 'no'),
                            closeOnNo: $j(this).dialog('option', 'closeOnNo')
                        };

                        opts.no(this.id);
                        if (opts.closeOnNo) {
                            $j(this).dialog('destroy');
                            $j(this).remove();
                        }
                    },
                    Yes: function() {
                        var opts = {
                            submit: $j(this).dialog('option', 'submit'),
                            validate: $j(this).dialog('option', 'validate'),
                            yes: $j(this).dialog('option', 'yes'),
                            button: $j(this).dialog('option', 'button'),
                            closeOnYes: $j(this).dialog('options', 'closeOnYes')
                        };

                        if (opts.validate()) {
                            opts.yes(this.id);
                            var form = $j('input[name=' + opts.button + ']').parents('form');
                            if (form.length > 0 && opts.submit) {
                                form[0].action += form[0].action.indexOf('?') !== -1 ? '&' : '?';
                                form[0].action += '_eventName=' + opts.button;
                                form[0].submit();
                            } else if (opts.closeOnYes) {
                                $j(this).dialog('close');
                            }
                        } else {
                            $j(this).dialog('close');
                        }
                    }
                },
                close: function() {
                    $j(this).dialog('destroy');
                    $j(this).remove();
                },
                open: function() {
                    if($j(this).dialog('option', 'title') == ''){
                        $j('.ui-dialog-titlebar-close.ui-corner-all').remove();
                        $j('.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix').remove();
                    }else{
                        $j('.ui-icon.ui-icon-closethick').remove();
                        $j('.ui-dialog-titlebar-close.ui-corner-all').remove();
                    }
                    $j('.ui-dialog-content.ui-widget-content').css('font-weight', 'normal');
                    $j('.ui-dialog-title').css('text-align','center');
                    $j('.ui-dialog-title').css('width','100%');
                },
                autoOpen: true
            }, options || {}));
        },
        // interface: G.fn.alertInform, Generic confirmation dialog as Alert so there's only one OK button
        alertInform: function(msg, okCallback) {
            G.fn.confirm({
                title:$.message.informationMessage,
                msg: msg || '',
                minHeight:125,
                buttons: {
                    OK: function() {
                        okCallback.call();
                        $j(this).dialog('close');
                    }
                }
            });
        },
        // interface: G.fn.alertWarning, Generic confirmation dialog as Alert so there's only one OK button
        alertWarning: function(msg, okCallback) {
            G.fn.confirm({
                title:$.message.informationWarning,
                msg: msg || '',
                minHeight:125,
                buttons: {
                    OK: function() {
                        okCallback.call();
                        $j(this).dialog('close');
                    }
                }
            });
        },
        // interface: G.fn.confirmationToSave, Pre-defined confirmation msg for add event
        confirmationToSave: function(options) {
            if (!(options instanceof Object)) {
                options = {
                    button:options
                };
            }
            var opts = $.extend({
                msg:$.message.confirmationToSave,
				title:$.message.titleConfirmationSave,
                minHeight:125
            }, options || {});
            G.fn.confirm(opts);
        },
        // interface G.fn.contextPath, global access to common function.
        contextPath: function(path){
            if(!path || path === ''){
                return ctxPath;
            } else { ctxPath = path; }
            return path;
        },
        // interface G.fn.getRadioBtnValue, get radio button value.
        getRadioBtnValue: function(name){
            if(!name){ return ''; }
            var value = jQuery('input[name='+name+']:checked').val();
            return value || '';
        },
        // interface G.fn.readonly, set textbox in selector have readonly attribute.
        readonly: function(selector, status){
            status ? $(selector).attr('readonly', 'readonly') : $(selector).removeAttr('readonly');
        },
        // interface G.fn.enable, set input to hace disable attribute.
        enable: function(selector){
            $(selector).removeClass(' ui-state-disabled');
            $(selector).removeAttr('disabled');
        },
        // interface G.fn.disable, remove attribute disable in input type.
        disable: function(selector){
            $(selector).addClass(' ui-state-disabled');
            $(selector).attr('disabled','disabled');
        },
        // interface initDatePicker, add jquery date picker ui to field
        initDatePicker: function(selector, options){
            // input only available via datepicker
            $(selector).attr('readonly', 'readOnly');
            // initialize defaults and merge with user defined options
            var defaults = $.extend({
                // make sure we'll use button trigger
                showOn: 'button',
                // date format
                dateFormat: $.message['date.format'],
                // button image
                buttonImage: ctxPath ? ctxPath + 'img/datepicker.png' : 'img/datepicker.png',
                // year range
                yearRange: $.message['yearRange'],
                // use button only for trigger
                buttonImageOnly: true,
                // enable change month
                changeMonth: true,
                // enable change year
                changeYear: true,
                // animation
                showAnim: 'fadeIn',
                // show button panel
                showButtonPanel: true,
                // override today text
                currentText: $.message['date.picker.clear'],
                // override done text
                closeText: $.message['date.picker.close'],
                // show calender text when pointer around the calendar icon
                buttonText: 'Calendar'
            }, options || {});
            $(selector).datepicker(defaults);
            $(selector).after('&nbsp;');
        }
    };
}(jQuery);

// add to dom:loaded event listener
G.InitModule = function(callback){
    jQuery(G.fn.init);
    jQuery(callback);
};

// Shortcut to jQuery
var $j = jQuery;