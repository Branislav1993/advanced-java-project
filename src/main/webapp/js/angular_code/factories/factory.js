module.exports = function (app) {

    app.factory("Members", Members);

    Members.$inject = ['Restangular'];
    function Members(Restangular){
        return Restangular.service('members');
    }
};