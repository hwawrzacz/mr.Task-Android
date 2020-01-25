const WebSocket = require('ws');

const serverPort = 3000
const wsServer = new WebSocket.Server({
    port: serverPort,
});

console.log(`Websocket server is ready and listening on port ${serverPort}`);

wsServer.on('connection', websocket => {
    websocket.on('message', message => {
        switch (message) {
            case 'NEW_CLIENT_CONNECTED': {
                console.log(`New client connected. Currently connected clients: ${wsServer.clients.size}`);
                break;
            }
            case 'CHANGES_MADE': {
                broadcast(websocket, 'CHANGES_MADE');
                break;
            }
            case 'CLIENT_DISCONNECTED': {
                console.log(`Client disconnected. Currently connected clients: ${wsServer.clients.size}`);
                break;
            }
            default: { break; }
        }
    });
});

wsServer.on('close', websocket => {
    broadcast(websocket, 'One connection closed')
})

const broadcast = (websocket, message) => {
    wsServer.clients.forEach(client => {
        if (client !== websocket) {
            client.send(message);
        }
    });
}

// For now, forget about it
class ClientsDatabase {
    constructor() {
        this.clients = new Map();
        this.nextId = 0
    }

    addNewClient(newClient) {
        this.findFirstFreeId();
        this.clients.set(this.nextId, newClient)
    }

    findFirstFreeId() {
        this.clients.forEach((value, key) => {
            if (value === null) {
                this.nextId = key;
            }
        });
    }
}