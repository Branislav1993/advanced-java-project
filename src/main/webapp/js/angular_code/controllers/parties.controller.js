module.exports = function (app) {

    app.controller("PartiesCtrl", PartiesCtrl);

    PartiesCtrl.$inject = ['Parties', 'localStorageService', 'dialogs'];
    function PartiesCtrl(Parties, localStorageService, dialogs) {

        var ctrl = this;
        ctrl.list = list;
        ctrl.remove = remove;
        ctrl.changePage = changePage;
        ctrl.search = search;
        ctrl.updateParty = updateParty;
        ctrl.createParty = createParty;

        ctrl.parties = [];
        ctrl.currentPage = null;
        ctrl.searchTerm = null;

        //GET ALL
        function list() {
            Parties.getList({page: ctrl.currentPage, query: ctrl.searchTerm}).then(function (parties) {
                ctrl.parties = parties;
            });
        }

        //SEARCH
        function search() {
            ctrl.currentPage = 1;
            list();
        }

        // DELETE
        function remove(partyId) {
            var dlg = dialogs.confirm("Are you sure?", "Do you want to delete selected party?", {size: "md"});
            dlg.result.then(function () {
                Parties.one(partyId).remove().then(function () {
                    list();
                });
            });
        }

        //CHANGE PAGE
        function changePage(page) {
            ctrl.currentPage = page;
            list();
        }

        function createParty() {
            localStorageService.remove("editedParty");
            window.location = '#/create-party';
        }

        function updateParty(party) {
            localStorageService.set("editedParty", party);
            window.location = "#/create-party";
        }

    }
};
