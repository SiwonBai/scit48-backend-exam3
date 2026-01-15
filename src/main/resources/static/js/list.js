$(document).ready(function () {

    loadBoardList("ALL"); // 최초 전체 조회

    $('#category').change(function () {
        let category = $(this).val();
        loadBoardList(category);
    });
});

// Ajax로 게시글 목록 로딩
function loadBoardList(category) {

    $.ajax({
        url: '/board/listAjax',
        type: 'get',
        data: { category: category },
        success: function (list) {

            let html = '';

            $.each(list, function (i, board) {

                let statusClass = board.status === 'OPEN' ? 'status-open' : 'status-closed';

                html += `
                    <tr>
                        <td>${board.category}</td>
                        <td>
                            <a href="/board/detail?boardNum=${board.boardNum}">
                                ${board.title}
                            </a>
                        </td>
                        <td>${board.member.memberId}</td>
                        <td class="${statusClass}">
                            ${board.status}
                        </td>
                        <td>(${board.headcnt} / ${board.capacity})</td>
                        <td>${formatDate(board.createDate)}</td>
                    </tr>
                `;
            });

            $('#boardBody').html(html);
        }
    });
}

// 날짜 포맷
function formatDate(dateStr) {
    let d = new Date(dateStr);
    return d.getFullYear() + '-' +
        String(d.getMonth()+1).padStart(2,'0') + '-' +
        String(d.getDate()).padStart(2,'0') + ' ' +
        String(d.getHours()).padStart(2,'0') + ':' +
        String(d.getMinutes()).padStart(2,'0') + ':' +
        String(d.getSeconds()).padStart(2,'0');
}