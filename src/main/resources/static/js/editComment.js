const editCommentModal = document.getElementById('editComment')
editCommentModal.addEventListener('show.bs.modal', function (event) {

    const button = event.relatedTarget

    const commentId = button.getAttribute('data-bs-comment-id')
    const commentBody = button.getAttribute('data-bs-comment-body')
    const commentCreatedAt = button.getAttribute('data-bs-comment-createdAt')
    const commentUpdatedAt = button.getAttribute('data-bs-comment-updatedAt')
    const commentAccountId = button.getAttribute('data-bs-comment-accountId')
    const commentPostId = button.getAttribute('data-bs-comment-postId')
    const commentUsername = button.getAttribute('data-bs-comment-username')

    const editedCommentId = editCommentModal.querySelector('#commentID')
    editedCommentId.value = commentId
    const editedCommentCreatedAt = editCommentModal.querySelector('#commentCreatedAt')
    editedCommentCreatedAt.value = commentCreatedAt
    const editedCommentUpdatedAt = editCommentModal.querySelector('#commentUpdatedAt')
    editedCommentUpdatedAt.value = commentUpdatedAt
    const editedCommentAccountId = editCommentModal.querySelector('#commentAccountId')
    editedCommentAccountId.value = commentAccountId
    const editedCommentPostId = editCommentModal.querySelector('#commentPostId')
    editedCommentPostId.value = commentPostId
    const editedCommentUsername = editCommentModal.querySelector('#commentUsername')
    editedCommentUsername.value = commentUsername
    const editedCommentBody = editCommentModal.querySelector('#commentBody')
    editedCommentBody.value=commentBody
})