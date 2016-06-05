module.exports = function (app) {

    app.controller("CreatePartyCtrl", CreatePartyCtrl);

    CreatePartyCtrl.$inject = ['Parties', 'localStorageService', 'Restangular', 'dialogs'];
    function CreatePartyCtrl(Parties, localStorageService, Restangular, dialogs) {

        var ctrl = this;
        ctrl.create = create;
        ctrl.update = update;

        ctrl.editedParty = {};
        ctrl.editMode = false;

        // CHECK PAGE MODE - EDIT || CREATE
        if (localStorageService.get("editedParty") != null) {
            ctrl.editedParty = localStorageService.get("editedParty");
            ctrl.editMode = true;
        }

        //CREATE
        function create() {
            Parties.post(ctrl.editedParty).then(function (response) {
                ctrl.editedParty = {};
                dialogs.notify("Success!", "Party successfully created!", {size: "md"});
            });
        }

        //UPDATE
        function update() {
            ctrl.putParty = Restangular.copy(ctrl.editedParty);
            ctrl.putParty.put().then(function (party) {
                ctrl.editedParty = party;
                dialogs.notify("Success!", "Party successfully updated!", {size: "md"});
            })
        }

    }
};
