/**
 * Performs an ajax DELETE call to delete user from domain Used in
 * domain/users.html
 * 
 * @param userId
 * @param domainId
 */
function deleteUserFromDomain(userId, domainId, username) {
    deleteUserDialog(domainId,userId,username);
}


function deleteUserDialog(domainId, userId, username) {

    bootbox.dialog({
        message : "Do you want to delete user " + username + " from domain ?",
        title : "Delete user",
        buttons : {
            success : {
                label : "Yes",
                className : "btn-success",
                callback : function() {
                    $.ajax({
                        url : '/executor/domain/' + domainId + '/user/' + userId,
                        type : 'DELETE',
                        success : function() {
                            bootbox.alert("Successfully deleted user " + username, function() {
                                window.location.reload();
                            });
                        },
                        error : function() {
                            bootbox.alert("Error while deleting user " + username);
                        }
                    });
                }
            },
            main : {
                label : "Cancel",
                className : "btn-primary"
            }
        }
    });
}
