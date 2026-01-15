$(document).ready(function () {
    $('#saveBtn').click(writeCheck);
});

/**
 * 게시글 입력값 유효성 검사
 */
function writeCheck() {

    let category = $('#category').val();
    let title = $('#title').val();
    let contents = $('#contents').val();
    let capacity = $('#capacity').val();

    if (category === '') {
        alert('카테고리를 선택하세요.');
        return;
    }

    if (title.trim() === '') {
        alert('제목을 입력하세요.');
        return;
    }

    if (contents.trim() === '') {
        alert('글내용을 입력하세요.');
        return;
    }

    if (capacity === '' || isNaN(capacity)) {
        alert('정원 수를 숫자로 입력하세요.');
        return;
    }

    $('#writeForm').submit();
}