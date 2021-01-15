/*默认规则 start*/
//ip格式
//ip格式
$.fn.bootstrapValidator.validators.ip = {
    //message: "ip格式不正确"
    validate: function(validator, $field, options) {

        var value = $field.val(),
            ipReg = /^(?:25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.(?:25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.(?:25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.(?:25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)$/;
        if (value === '') {
            return true;
        }
        return ipReg.test(value);
    }
};

//password格式
$.fn.bootstrapValidator.validators.pw = {
    //message: "必须包含数字、英文字母、特殊字符"
    validate: function(validator, $field, options) {
        var value = $field.val(),
            ipReg = /.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*_])./;
        if (typeof value != 'string' || !ipReg.test(value)) {
            return false;
        }
        return true;
    }
};

//不允许有空格
$.fn.bootstrapValidator.validators.noSpace = {
    //message: "必须包含数字、英文字母、特殊字符"
    validate: function(validator, $field, options) {

        var value = $field.val();
        if (typeof value != 'string' || value.indexOf(' ') > -1) {
            return false;
        }
        return true;
    }
};

//网关格式
$.fn.bootstrapValidator.validators.mask = {
    //message: "网关不可达"
    validate: function(validator, $field, options) {

        var ipArr = $field.parent().parent().find('input[name="ip"]').val().split('.'),
            gatewayArr = $field.parent().parent().find('input[name="gateway"]').val().split('.'),
            value = $field.val(),
            netmaskArr = value.split('.'),
            len = 4,
            i = 0;
        if (ipArr.length !== len || gatewayArr.length !== len || netmaskArr.length !== len) {
            return false;
        }
        for (; i < len; i++) {
            if ((ipArr[i] & netmaskArr[i]) !== (gatewayArr[i] & netmaskArr[i])) {
                return false;
            }
        }
        return true;
    }
};

//邮箱 表单验证规则
$.fn.bootstrapValidator.validators.mail = {
    //message: "邮箱格式不正确"
    validate: function(validator, $field, options) {
        var mail = /^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$/,
            value = $field.val();
        return mail.test(value);
    }
};

//电话验证规则
$.fn.bootstrapValidator.validators.phone = {
    //message: "0371-68787027"
    validate: function(validator, $field, options) {
        var phone = /^0\d{2,3}-\d{7,8}$/,
            value = $field.val();
        return phone.test(value);
    }
};

//区号验证规则
$.fn.bootstrapValidator.validators.ac = {
    //message: "区号如：010或0371"
    validate: function(validator, $field, options) {
        var ac = /^0\d{2,3}$/,
            value = $field.val();
        return ac.test(value);
    }
};

//无区号电话验证规则
$.fn.bootstrapValidator.validators.noactel = {
    //message: "电话格式如：68787027"
    validate: function(validator, $field, options) {
        var noactel = /^\d{7,8}$/,
            value = $field.val();
        return noactel.test(value);
    }
};
/*默认规则 end*/

$.fn.extend({
    initBV: function(config) { //初始化函数
        if (this.length == 0 || this[0].tagName !== 'FORM') {
            return false;
        }
        var $form = this.eq(0),
            $inputs = $form.find('input'),
            $errors = $form.find('.errors'),
            $itemBtn = $form.find('.item-btn');
        //让ul.errors中显示验证项
        function initTips(fields) {
            var validator, notEmpty, $errField;

            fields = fields.fields || fields;

            if (!fields) return false;

            for (var field in fields) {
                $errField = $form.find('#errors-' + field);
                $errField.hide().find('li').remove();
                validators = fields[field].validators;
                notEmpty = false;
                for (var vali in validators) {
                    $('<li/>')
                        .addClass('text-left')
                        .attr('data-field', field)
                        .attr('data-vali', vali)
                        .html(validators[vali].message)
                        .appendTo($errField);
                    if (vali == 'notEmpty') {
                        notEmpty = true;
                    }
                }
                if (notEmpty) {
                    $errField.data('status', 'error');
                } else {
                    $errField.data('status', 'success');
                }
            }
            return false;
        }

        initTips(config.fields);

        $form.bootstrapValidator(config)
            .on('success.form.bv', function(e, data) { //点击提交之后
                // Prevent form submission
                e.preventDefault();
                return false;
            }).on('success.field.bv', function(e, data) {
            var removeClass, successClass;
            if (data.element[0].value) {
                //验证成功
                console.log('real success')
                removeClass = 'error';
                addClass = 'success';
            } else {
                //验证的其实是''(空字符串)，但也被算是success事件
                console.log('not success');
                removeClass = 'error success';
                addClass = 'normal';
            }
            $errors.hide();
            $form.find('#errors-' + data.field).show().data('status', 'success').find('li').each(function(idx, item) {
                $(item).removeClass(removeClass).addClass(addClass);
            });
        }).on('error.field.bv', function(e, data) {
            // data.bv      --> The BootstrapValidator instance
            // data.field   --> The field name
            // data.element --> The field element

            // Get the messages of field
            var field = data.field;
            var messages = data.bv.getMessages(data.element);

            // Remove the field messages if they're already available
            $errors.hide();
            $form.find('#errors-' + data.field).show().data('status', 'error').find('li').each(function(idx, item) {
                item = $(item);
                if (messages.indexOf(item.text().replace('&', '&amp;')) > -1 || config.fields[data.field].validators.notEmpty && messages.indexOf(config.fields[data.field].validators.notEmpty.message) > -1) {
                    item.removeClass('success').addClass('error');
                } else {
                    item.removeClass('error').addClass('success');
                }
            });

            // Hide the default message
            // $field.data('bv.messages') returns the default element containing the messages
            data.element
                .data('bv.messages')
                .find('.help-block[data-bv-for="' + data.field + '"]')
                .hide();
        });

        $inputs.blur(function(e) {
            $errors.hide();
        })
        $inputs.focus(function(e) {
            $errors.hide();
            $(this).trigger('input');
            $(this).parent().find('.totalTip').hide();
            $form.find('#errors-' + this.name).show();
        })
        $itemBtn.click(function(e) {
            e.preventDefault();
            $form.find('input').trigger('input');
            $('.errors').hide();
            return false;
        });
    },
    valiFields: function(fields) { //验证fields是否验证通过，未通过则提示信息
        var status = true,
            fieldStatus, $errField, $errFiePar, $totalTip;

        fields = fields.fields || fields;

        if (!fields) return false;

        for (var field in fields) {
            $errField = $('#errors-' + field);
            fieldStatus = $errField.data('status');

            if (fieldStatus == 'error') {
                $errFiePar = $errField.parent(),
                    $totalTip = $errFiePar.find('.totalTip');
                if ($totalTip.length) {
                    $totalTip.show();
                } else {
                    $errFiePar.append('<span class="totalTip text-left">' + fields[field].message + '</span>');
                }
            }
            status = status && fieldStatus == 'success';
        }
        return status;
    }
});