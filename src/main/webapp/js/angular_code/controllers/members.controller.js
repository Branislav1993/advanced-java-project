module.exports = function (app) {

    app.controller("MembersCtrl", MembersCtrl);

    MembersCtrl.$inject = ['Members', 'localStorageService'];
    function MembersCtrl(Members, localStorageService) {

        var ctrl = this;
        ctrl.list = list;
        ctrl.remove = remove;
        ctrl.changePage = changePage;
        ctrl.search = search;
        ctrl.updateMember = updateMember;
        ctrl.createMember = createMember;

        ctrl.members = [];
        list();
        ctrl.currentPage = null;
        ctrl.searchTerm = null;

        //GET ALL
        function list() {
            Members.getList({page: ctrl.currentPage, query: ctrl.searchTerm}).then(function (members) {
                ctrl.members = members;
            });
        }

        //SEARCH
        function search() {
            ctrl.currentPage = 1;
            list();
        }

        // DELETE
        function remove(memberId) {
            Members.one(memberId).remove().then(function () {
                list();
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
            window.location = "#/create-member"
        }

    }
};
