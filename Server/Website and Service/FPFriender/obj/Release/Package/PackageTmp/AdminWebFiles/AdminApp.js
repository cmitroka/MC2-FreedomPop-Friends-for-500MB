function DoInitEmpty() {

}

function BuildDynamicPage(WebserviceName, TitleOfPage) {
    $.ajax({
        type: "POST",
        url: "CJMAdminAppWs.asmx/" + WebserviceName,
        dataType: "text",
        success:
            function (xml) {
                var temp1 = PMEncodedHTMLToText(xml);
                var temp2 = RemoveHeader(temp1);
                try {
                    $('#dynamicpage_header').text(TitleOfPage);
                    $('#dynamicpage_table').html(temp2).trigger('create');
                } catch (e) {
                    $('#dynamicpage_table').html(temp2);
                }
                $.mobile.changePage("#dynamicpage", { transition: "slideup" });

            },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function BuildDynamicPageV2(pFromWhere, TitleOfPage) {


    $.ajax({
        type: "POST",
        url: "CJMAdminAppWs.asmx/RunQuery",
        dataType: "text",
        data: { pFromWhere: pFromWhere },
        success:
            function (xml) {
                var temp1 = PMEncodedHTMLToText(xml);
                var temp2 = RemoveHeader(temp1);
                try {
                    $('#dynamicpage_header').text(TitleOfPage);
                    $('#dynamicpage_table').html(temp2).trigger('create');
                } catch (e) {
                    $('#dynamicpage_table').html(temp2);
                }
                $.mobile.changePage("#dynamicpage", { transition: "slideup" });

            },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}
function DoAdminLogin(pLogin, pPassword) {
    document.getElementById('username').value = pLogin;
    document.getElementById('password').value = pPassword;
    $.mobile.changePage("#main");
}
function BuildDynamicPageV3(pFromWhere, TitleOfPage) {
    var pUser = document.getElementById('username').value;
    var pPass = document.getElementById('password').value;

    $.ajax({
        type: "POST",
        url: "AdminAppWs.asmx/RunQuery",
        dataType: "text",
        data: { pFromWhere: pFromWhere, pUsername: pUser, pPassword: pPass },
        success:
            function (xml) {
                var temp1 = PMEncodedHTMLToText(xml);
                var temp2 = RemoveHeader(temp1);
                try {
                    $('#dynamicpage_header').text(TitleOfPage);
                    $('#dynamicpage_table').html(temp2).trigger('create');
                } catch (e) {
                    $('#dynamicpage_table').html(temp2);
                }
                $.mobile.changePage("#dynamicpage", { transition: "slideup" });

            },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}
function BuildUserReport00(pUserID, TitleOfPage) {
    var pUser = document.getElementById('username').value;
    var pPass = document.getElementById('password').value;
    $.ajax({
        type: "POST",
        url: "CJMAdminAppWs.asmx/MakeUserReport",
        dataType: "text",
        data: { pUserID: pUserID, pUsername: pUser, pPassword: pPass },
        success:
            function (xml) {
                var temp1 = PMEncodedHTMLToText(xml);
                var temp2 = RemoveHeader(temp1);
                try {
                    $('#dynamicpage_header').text(TitleOfPage);
                    $('#dynamicpage_table').html(temp2).trigger('create');
                } catch (e) {
                    $('#dynamicpage_table').html(temp2);
                }
                $.mobile.changePage("#dynamicpage", { transition: "slideup" });

            },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function DoGetOverrideCode() {
    $.ajax({
        type: "POST",
        url: "AdminAppWs.asmx/GetOverrideCode",
        dataType: "text",
        data: { pCodeIn: document.getElementById('txtCodeIn').value, pKeyIn: document.getElementById('txtEncKey').value },
        success:
            function (xml) {
                //alert(xml);
                var temp1 = PMEncodedHTMLToText(xml);
                var temp2 = RemoveHeader(temp1);
                document.getElementById('txtOverrideCode').value = temp2;
            },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });
}
function DoClearScreen() {
    var z = document.getElementById('txtCodeIn').value;
    document.getElementById('txtCodeIn').value = "";
    document.getElementById('txtOverrideCode').value = "";

}
function RemoveHeader(dataIn) {
    var retVal = PMGetSubstring(dataIn, 'App.mc2techservices.com">', '</string>');
    return retVal;
}

function PMGetSubstring(fullString, startString, endString) {
    var UCfullString = fullString.toUpperCase();
    startString = startString.toUpperCase();
    endString = endString.toUpperCase();
    if (endString == "") endString = "NEVERFINDTHISDYHJDYUXKSSDULSJDJ";
    var indexOfStart = UCfullString.indexOf(startString)
    var successStep = 0;
    if (indexOfStart == -1) {
    }
    else {
        successStep = successStep + 1;
    }
    if (successStep == 0) return "";
    indexOfStart = indexOfStart + (startString.length - 1);

    var adjustedFullString = fullString.substring(indexOfStart, 999999);
    var UCadjustedFullString = adjustedFullString.toUpperCase();
    var indexOfEnd = UCadjustedFullString.indexOf(endString)
    if (indexOfEnd == -1) {
        if (endString == "NEVERFINDTHISDYHJDYUXKSSDULSJDJ") {
            successStep = successStep + 1;
            indexOfEnd = 999999;
        }
    }
    else {
        successStep = successStep + 1;
    }
    if (successStep < 2) return "";
    indexOfEnd = indexOfEnd;  //not sure if this is needed, may be trimming the last char...
    var retval = adjustedFullString.substring(1, indexOfEnd);
    return retval;
}

function PMHTMLEscape(str) {
    return String(str)
            .replace(/&/g, '&amp;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;');
}

function PMEncodedHTMLToText(str) {
    //var ucstr=str.toUpperCase();
    return String(str)
            .replace(/&lt;/g, '<')
            .replace(/&gt;/g, '>')
            .replace(/&amp;/g, '&')
            .replace(/&quot;/g, '"')
            .replace(/&#39;/g, "'");
}
