module.exports = function (app) {

    app.controller("CreateMemberCtrl", CreateMemberCtrl);

    CreateMemberCtrl.$inject = ['Members', 'localStorageService', 'Towns', 'Restangular', 'dialogs'];
    function CreateMemberCtrl(Members, localStorageService, Towns, Restangular, dialogs) {

        var ctrl = this;

        ctrl.create = create;
        ctrl.update = update;

        ctrl.editedMember = {};
        ctrl.editMode = false;

        ctrl.towns = [];
        listTowns();

        //GET ALL TOWNS
        function listTowns() {
            Towns.getList().then(
                function (towns) {
                    ctrl.towns = towns.plain();
                },
                function (response) {
                    dialogs.notify("Error!", response.data.error, null);
                });
        }

        // CHECK PAGE MODE - EDIT || CREATE
        if (localStorageService.get("editedMember") != null) {
            ctrl.editedMember = localStorageService.get("editedMember");
            if (ctrl.editedMember.dateOfBirth) {
                var day = ctrl.editedMember.dateOfBirth.substring(0, ctrl.editedMember.dateOfBirth.indexOf('.'));
                var month = ctrl.editedMember.dateOfBirth.substring(ctrl.editedMember.dateOfBirth.indexOf('.') + 1, ctrl.editedMember.dateOfBirth.lastIndexOf('.'));
                var year = ctrl.editedMember.dateOfBirth.substring(ctrl.editedMember.dateOfBirth.lastIndexOf('.') + 1, ctrl.editedMember.dateOfBirth.length);
                ctrl.editedMember.dateOfBirth = new Date(year, month - 1, day);
            }
            ctrl.editMode = true;
        }

        //CREATE
        function create() {
            Members.post(ctrl.editedMember).then(
                function (response) {
                    ctrl.editedMember = {};
                    dialogs.notify("Success!", "Member successfully created!", {size: "md"});
                },
                function (response) {
                    dialogs.notify("Error!", 'Status: ' + response.status + ' Message: ' + response.data.error, {size: "md"});
                });
        }

        //UPDATE
        function update() {
            ctrl.putMember = Restangular.copy(ctrl.editedMember);
            ctrl.putMember.put().then(
                function (member) {
                    ctrl.editedMember = member;
                    dialogs.notify("Success!", "Member successfully updated!", {size: "md"});
                },
                function (response) {
                    dialogs.notify("Error!", 'Status: ' + response.status + ' Message: ' + response.data.error, {size: "md"});
                })
        }

        // DATE PICKER SETUP START
        ctrl.open = function () {
            ctrl.popup.opened = true;
        };

        ctrl.dateOptions = {
            formatYear: 'yy',
            startingDay: 1,
            timezone: 'UTC+1'
        };

        ctrl.format = 'dd.MM.yyyy';

        ctrl.popup = {
            opened: false
        };
        // DATE PICKER SETUP END
    }
};
