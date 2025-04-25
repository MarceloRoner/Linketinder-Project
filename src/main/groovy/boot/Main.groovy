package boot

import context.AppContext
import view.LinketinderView
import utils.ConnectionFactory

def ctx  = new AppContext()
def view = new LinketinderView(
        ctx.facade,
        ctx.candidatoCtl,
        ctx.empresaCtl,
        ctx.vagaCtl)

view.iniciar()

ctx.closeConnection()
ConnectionFactory.getInstance().close()
