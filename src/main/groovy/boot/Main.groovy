package boot

import context.AppContext
import app.LinketinderApp

def ctx = new AppContext()
def app = new LinketinderApp(
        ctx.candidatoService,
        ctx.empresaService,
        ctx.vagaService,
        ctx.competenciaService,
        ctx.curtidaService
)
app.iniciar()
ctx.closeConnection()
