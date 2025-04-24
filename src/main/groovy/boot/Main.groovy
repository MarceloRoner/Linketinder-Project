package boot

import utils.ConnectionFactory;
import context.AppContext
import app.LinketinderApp

def ctx = new AppContext()
def app = new LinketinderApp(ctx.facade)
app.iniciar()
ctx.closeConnection()
utils.ConnectionFactory.instance.close()
