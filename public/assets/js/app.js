var App = {
    templates: {
        loaded: [],
        load: function (url, callback) {
            var loaded = App.templates.loaded[url];
            if (loaded != undefined) {
                callback(loaded);
                return loaded;
            }
            return App.templates.ajax(url, function (response) {
                App.templates.loaded[url] = response;
                if (callback != undefined) {
                    return callback(response);
                }
            });
        },
        ajax: function (url, callback) {
            $.ajax({
                url: "/assets/html/" + url + ".html",
                cache: true,
                contentType: false,
                processData: false,
                dataType: 'html',
                type: 'GET',
                success: function (data) {
                    if (callback != undefined) {
                        return callback(data);
                    }
                }
            });
        },
        replace: function (selector, template, restObj) {
            $(selector).html(App.utils.mustache(template, restObj));
        }
    },
    rest: function (ajaxObject) {
        ajaxObject.url = '/api' + ajaxObject.url;
        ajaxObject.cache = false;
        if (ajaxObject.data != undefined && ajaxObject.method != 'GET' && ajaxObject.contentType == undefined) {
            ajaxObject.data = JSON.stringify(ajaxObject.data);
        }
        ajaxObject.contentType = (ajaxObject.contentType == undefined) ? 'application/json; charset=UTF-8' : ajaxObject.contentType;
        return $.ajax(ajaxObject);
    },
    utils: {
        getQueryParam: function (key) {
            var result = "",
                tmp = [];
            location.search
                .replace("?", "")
                //.substr(1)
                .split("&")
                .forEach(function (item) {
                    tmp = item.split("=");
                    if (tmp[0] === key) result = decodeURIComponent(tmp[1]);
                });
            return result;
        },
        getCookie: function (name) {
            var matches = document.cookie.match(new RegExp(
                "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
            ));
            return matches ? decodeURIComponent(matches[1]) : undefined;
        },
        setCookie: function (name, value, options) {
            options = options || {};

            var expires = options.expires;

            if (typeof expires == "number" && expires) {
                var d = new Date();
                d.setTime(d.getTime() + expires * 1000);
                expires = options.expires = d;
            }
            if (expires && expires.toUTCString) {
                options.expires = expires.toUTCString();
            }

            value = encodeURIComponent(value);

            var updatedCookie = name + "=" + value;

            for (var propName in options) {
                updatedCookie += "; " + propName;
                var propValue = options[propName];
                if (propValue !== true) {
                    updatedCookie += "=" + propValue;
                }
            }
            document.cookie = updatedCookie;
        },
        deleteCookie: function (name) {
            App.utils.setCookie(name, "", {
                expires: -1
            })
        },
        mustache: function (template, restObj) {
            return Mustache.to_html(template, {entity: restObj});
        },
    },
    user: {
        model: null,
        isLogined: function () {
            return (!!App.user.model);
        },
    },
    router: new Router(),
};




$(document).ready(function () {
    App.router.start();
    //if (App.utils.getCookie("JSESSIONID")) {
    //    App.rest({
    //        method: 'GET',
    //        url: '/user/current',
    //        success: function (restObject) {
    //            App.user.model = restObject;
    //            App.router.navigate('/lk');
    //        },
    //        error:function(jqXHR){
    //            App.utils.deleteCookie("JSESSIONID");
    //        },
    //    });
    //}
});

App.router.route('/', function () {
    if(App.user.isLogined()){
        App.router.navigate('/lk');
    }
    App.templates.load('login/main', function (template) {
        App.templates.replace('body', template);
    });
});

/*

$(document).on('click', '#loginMainAuthBtn', function () {
    $(".auth.error_message").html('');
    App.rest({
        method: 'POST',
        url: '/user/auth',
        data: {
            email: $(this).closest('.win_reg_info').find('[name="email"]').val(),
            password: $(this).closest('.win_reg_info').find('[name="password"]').val(),
        },
        success: function (restObject) {
            App.user.model = restObject;
            App.router.navigate('/lk');
        },
        error: function (jqXHR) {
            $(".auth.error_message").html(jqXHR.responseJSON.message);
        }
    });
});

$(document).on('click', '#loginEmailSendBtn', function () {
    $(".email.error_message").html('');
    App.rest({
        method: 'POST',
        url: '/user/email-send',
        data: {
            email: $(this).closest('.win_reg_info').find('[name="email"]').val(),
        },
        success: function (restObject) {
            App.user.model = restObject;
            App.router.navigate('/');
        },
        error: function (jqXHR) {
            $(".email.error_message").html(jqXHR.responseJSON.message);
        }
    });
});

$(document).on('click', '#loginPasswordRestoreBtn', function () {
    $(".password.error_message").html('');
    var password1 = $(this).closest('.win_reg_info').find('[name="password"]').val();
    var password2 = $(this).closest('.win_reg_info').find('[name="password2"]').val();
    var token = App.utils.getQueryParam('token');
    App.rest({
        method: 'POST',
        url: '/user/password-restore',
        data: {
            password: password1,
            resetToken: token,
        },
        success: function (restObject) {
            App.user.model = restObject;
            App.router.navigate('/');
        },
        error: function (jqXHR) {
            $(".password.error_message").html(jqXHR.responseJSON.message);
        }
    });
});

App.router.route('/logout', function () {
    App.rest({
        method: 'POST',
        url: '/user/logout',
    });
    App.utils.deleteCookie("JSESSIONID");
    App.router.navigate('/');
});

App.router.route('/email', function () {
    if(App.user.isLogined()){
        App.router.navigate('/lk');
    }
    App.templates.load('login/email', function (template) {
        App.templates.replace('body', template);
    });
});
App.router.route('/password', function () {
    if(App.user.isLogined()){
        App.router.navigate('/lk');
    }
    App.templates.load('login/password', function (template) {
        App.templates.replace('body', template);
    });
});
App.router.route('/lk', function () {
    if(!App.user.isLogined()){
        return App.router.navigate('/');
    }
    App.templates.load('lk/main', function (template) {
        App.rest({
            method: 'GET',
            url: '/data',
            success: function (restObject) {
                var dataObject=[];
                $(restObject).each(function(k,v){
                    dataObject[v.name]=v;
                });
                App.templates.replace('body', template);
                lkMainForm.init(dataObject);
            },
        });
    });
});

*/