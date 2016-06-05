module.exports = function (app) {

    app.controller("MembersCtrl", MembersCtrl);

    MembersCtrl.$inject = ['Members', 'localStorageService', 'dialogs'];
    function MembersCtrl(Members, localStorageService, dialogs) {

        var ctrl = this;
        ctrl.list = list;
        ctrl.remove = remove;
        ctrl.changePage = changePage;
        ctrl.search = search;
        ctrl.updateMember = updateMember;
        ctrl.createMember = createMember;
        ctrl.showBiography = showBiography;
        ctrl.seeSpeeches = seeSpeeches;

        ctrl.members = [];
        ctrl.currentPage = null;
        ctrl.searchTerm = null;

        //GET ALL
        function list() {
            Members.getList({page: ctrl.currentPage, query: ctrl.searchTerm}).then(function (members) {
                ctrl.members = members;
            },
            function (response) {
                dialogs.notify("Error!", response.data.error, null);
            });
        }

        //SEARCH
        function search() {
            ctrl.currentPage = 1;
            list();
        }

        // DELETE
        function remove(memberId) {
            var dlg = dialogs.confirm("Are you sure?", "Do you want to delete selected member?", {size: "md"});
            dlg.result.then(function () {
                Members.one(memberId).remove().then(function () {
                    list();
                }, function (response) {
                    dialogs.notify("Error!", response.data.error, null);
                });
            });
        }

        //CHANGE PAGE
        function changePage(page) {
            ctrl.currentPage = page;
            list();
        }

        function createMember() {
            localStorageService.remove("editedMember");
            window.location = '#/create-member';
        }

        function updateMember(member) {
            localStorageService.set("editedMember", member);
            window.location = "#/create-member";
        }

        function showBiography(m) {
            dialogs.notify(m.name + ' ' + m.lastName + ' biography', m.biography, null);
        }

        function seeSpeeches(m) {
            localStorageService.set("member", m);
            window.location = '#/member-speeches';
        }

    }
};
