module.exports = function (app) {

    app.controller("CreateSessionCtrl", CreateSessionCtrl);

    CreateSessionCtrl.$inject = ['Sessions', 'localStorageService', 'Restangular', 'dialogs'];
    function CreateSessionCtrl(Sessions, localStorageService, Restangular, dialogs) {

        var ctrl = this;

        ctrl.create = create;
        ctrl.update = update;

        ctrl.editedSession = {};
        ctrl.editMode = false;

        // CHECK PAGE MODE - EDIT || CREATE
        if (localStorageService.get("editedSession") != null) {
            ctrl.editedSession = localStorageService.get("editedSession");
            if (ctrl.editedSession.date) {
                var day = ctrl.editedSession.date.substring(0, ctrl.editedSession.date.indexOf('.'));
                var month = ctrl.editedSession.date.substring(ctrl.editedSession.date.indexOf('.') + 1, ctrl.editedSession.date.lastIndexOf('.'));
                var year = ctrl.editedSession.date.substring(ctrl.editedSession.date.lastIndexOf('.') + 1, ctrl.editedSession.date.length);
                ctrl.editedSession.date = new Date(year, month - 1, day);
            }
            ctrl.editMode = true;
        }

        //CREATE
        function create() {
            console.log(JSON.stringify(ctrl.editedSession));
            Sessions.post(ctrl.editedSession).then(
                function (response) {
                    ctrl.editedSession = {};
                    dialogs.notify("Success!", "Session successfully created!", {size: "md"});
                },
                function (response) {
                    dialogs.notify("Error!", 'Status: ' + response.status + ' Message: ' + response.data.error, {size: "md"});
                });
        }

        //UPDATE
        function update() {
            ctrl.putSession = Restangular.copy(ctrl.editedSession);
            ctrl.putSession.put().then(
                function (session) {
                    ctrl.editedSession = session;
                    dialogs.notify("Success!", "Session successfully updated!", {size: "md"});
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
