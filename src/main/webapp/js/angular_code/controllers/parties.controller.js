module.exports = function (app) {

    app.controller("PartiesCtrl", PartiesCtrl);

    PartiesCtrl.$inject = ['Parties'];
    function PartiesCtrl(Parties) {

        var ctrl = this;
        ctrl.create = create;
        ctrl.remove = remove;
        //ctrl.update = update;

        ctrl.parties = [];
        list();
        ctrl.newParty = {};

        //GET ALL
        function list() {
            Parties.getList().then(function (parties) {
                ctrl.parties = parties;
            });
        }

        //CREATE
        function create() {
            Parties.post(ctrl.newParty).then(function (response) {
                ctrl.newParty = {};
                console.log(JSON.stringify(response));
            });
        }

        // DELETE
        function remove(partyId) {
            Parties.one(partyId).remove().then(function () {
                ctrl.list();
            });
        }
    }
};
