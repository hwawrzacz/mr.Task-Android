const connection = new WebSocket('ws://192.168.0.105:3000');

const textarea = document.querySelector('#received');
const buttond_send = document.querySelector('#send');

connection.addEventListener('open', () => {
    textarea.value = `Connected`;
    connection.send('NEW_CLIENT_CONNECTED');
});

connection.addEventListener('message', () => {
    textarea.value = `${textarea.value} \nNew change`;
});


buttond_send.addEventListener('click', () => {
    connection.send('CHANGES_MADE');
});