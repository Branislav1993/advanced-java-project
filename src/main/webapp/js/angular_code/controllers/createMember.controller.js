module.exports = function (app) {

    app.controller("CreateMemberCtrl", CreateMemberCtrl);

    CreateMemberCtrl.$inject = ['Members', 'localStorageService', 'Towns', 'Restangular'];
    function CreateMemberCtrl(Members, localStorageService, Towns, Restangular) {

        var ctrl = this;
        ctrl.create = create;
        ctrl.update = update;

        ctrl.editedMember = {};
        ctrl.editMode = false;

        //CREATE
        function create() {
            Members.post(ctrl.editedMember).then(function (response) {
                ctrl.editedMember = {};
                console.log(JSON.stringify(response));
            });
        }

        function update() {
            ctrl.putMember = Restangular.copy(ctrl.editedMember);
            ctrl.putMember.put().then(function (member) {
                ctrl.editedMember = member;
                alert(JSON.stringify(member));
            })
        }

        if (localStorageService.get("editedMember") != null) {
            ctrl.editedMember = localStorageService.get("editedMember");
            console.log(localStorageService.get("editedMember"));
            ctrl.editMode = true;
        }
    }
};
