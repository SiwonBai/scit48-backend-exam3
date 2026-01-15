$(document).ready(function () {
    console.log("join.js loaded"); // ⭐ 이거 찍히는지
    $('#joinButton').click(joinForm); // 회원가입 유효성 검사
});

function joinForm() {
    console.log("joinButton clicked"); // ⭐ 이거 찍히는지

    let id = $('#memberId').val();
    let pw = $('#password').val();
    let name = $('#name').val();
    let email = $('#email').val();

    if (id.length < 3 || id.length > 10) {
        alert("ID는 3~10자로 입력하세요.");
        return;
    }

    if (pw.length < 3 || pw.length > 10) {
        alert("비밀번호는 3~10자로 입력하세요.");
        return;
    }

    if (name.trim() === '') {
        alert("이름을 입력하세요.");
        return;
    }

    if (email.trim() === '') {
        alert("이메일을 입력하세요.");
        return;
    }

    // 모든 검사 통과 시에만 서버로 전송
    $('#joinForm').submit();
}