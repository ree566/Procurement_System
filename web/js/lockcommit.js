var isCommitted = false;//表单是否已经提交标识，默认为false
function dosubmit() {
    if (isCommitted == false) {
        isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true
        return true;//返回true让表单正常提交
    } else {
        return false;//返回false那么表单将不提交
    }
}