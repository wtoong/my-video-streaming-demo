const form = document.querySelector('#video-form');
const videoDiv = document.querySelector('#video-player');
const videoScreen = document.querySelector('#video-screen');

const queryParams = Object.fromEntries(new URLSearchParams(window.location.search));

form.addEventListener('submit', ev => {
    ev.preventDefault();
    let data = new FormData(form);
    fetch('http://localhost:8080/video', {
        method: 'POST',
        body: data
    }).then(result => result.text()).then(_ => {
        window.location.reload();
    });

});
