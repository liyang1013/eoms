import { app, protocol, BrowserWindow } from 'electron'

protocol.registerSchemesAsPrivileged([
  { scheme: 'app', privileges: { secure: true, standard: true } }
])

async function createWindow() {
  const win = new BrowserWindow({
    width: 1200,
    height: 700,
    autoHideMenuBar: true,
    webPreferences: {
    }
  })
    // await win.loadURL('http://localhost/invoice')
  await  win.loadFile('index.html')
}

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit()
})

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) createWindow()
})

app.whenReady().then(() => {
  createWindow()
  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow()
  })
})


