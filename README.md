### Programação Orientada à Objetos

1. Para o desenvolvimento de uma determinada aplicação em Java, deseja-se construir
   uma Classe para a abstração de Objeto do mundo real que representa um Robô
   (Classe `Robot`), no qual devemos utilizar os seguintes atributos:

    ```
    name            Nome do robô (tipo String)
    speed           Velocidade atual do robô em m/s (tipo real)
    temperature     Temperatura atual do robô em ºF (tipo real)
    status          Situação atual do robô, podendo assumir
                    5 possibilidades possíveis (tipo byte):
                    1=StandBy, 2=Moving, 3=Exploring, 4=Stopped,
                    5=ReturningHome
    power           Carga atual da bateria em percentual
                    (0% a 100%) (tipo inteiro)
    ```

2. Devemos implementar 3 construtores para Robot, sendo um padrão (_default_) sem
   parâmetros, um recebendo o nome do robô criado e outro com todos os atributos
   a Classe.

3. Deseja-se encapsular os atributos de forma privada para prover um acesso
   controlado dos mesmos por meio de acessos exclusivos pelos métodos acessores
   (`get`) e métodos modificadores (`set`).

4. O atributo status terá o seguinte comportamento: A atualização do status só
   será realizada de forma automática e internamente na Classe, por meio de
   comportamentos específicos do Objeto, não podendo em hipótese alguma ser
   atualizado pelo cliente da Classe, e será manipulado por meio de um número
   simples do tipo `byte` (1 a 5).

5. O estado do Objeto será apresentado em Console com todos seus atributos em
   única linha, implementado através de um método chamado `show()`, mostrando
   o estado do objeto formatado pelo método `toString()` da Classe.

6. Implementar um novo atributo para a velocidade máxima que poderá ser atingida
   pelo robô, em m/s, chamado `maxSpeed`.

7. O atributo de velocidade atual terá sua atualização exclusivamente realizada
   pelos seguintes comportamentos do Objeto:

    ```
    stop()          Zerar a velocidade atual do robô, atualizando seu status, e
                    ainda reduzindo sua temperatura a 10% da temperatura atual.
    speedUp(int)    Incrementa a velocidade atual do robô, respeitando a
                    velocidade máxima permitida e seu status, atualizando-o.
    speedUp()       Atualiza a velocidade do robô, em +10% da velocidade atual,
                    respeitando a velocidade máxima permitida.
    speedDown(int)  Reduz a velocidade atual do robô, em unidades definidas
                    no parâmetro do método, respeitando o valor zero (Stopped).
    breaking()      Atualiza a velocidade do robô, em -10% da velocidade atual,
                    respeitando a velocidade máxima permitida.
    ```

8. Implementar os seguintes métodos de mudança de status:

    ```
    standBy()       desliga totalmente zerando a velocidade e leva a temperatura a
                    zero, mantendo sua carga atual.
    exploring()     só mudará de estado se seu estado atual estiver “Stopped” e
                    eleva sua temperatura em 20ºF.
    stop()          zerar a velocidade (mudança de velocidade para zero).
    returning()     mantém o estado da temperatura e velocidade, e somente
                    mudará o status se estiver em “Moving”.
    ```

9. O comportamento da temperatura será o seguinte:
   A cada incremento de 1 m/s na velocidade, será acrescido 0.5ºF na temperatura.
   O inverso ocorre idêntico na forma contrária para a redução da velocidade.
   Ao parar, terá sua temperatura reduzida a 10% da atual.
   Ao entrar em _StandBy_, terá a temperatura zerada (método `standBy()`).

10. O comportamento da carga será o seguinte:

    ```
    charge()        aumenta 10% na carga respeitando a carga máxima.
    fullCharge()    eleva a carga para seu valor máximo.
                    A cada aumento de 1 m/s a carga reduz em 0.25%
                    Ao explorar a carga reduz em 10%, respeitando a carga mínima.
                    Ao zerar a carga, entrará automaticamente em “StandBy”
    
    (*) Métodos get/set
     getName (public)
     getSpeed (public)
     getMaxSpeed (public)
     getTemperature (public)
     getStatus (public)
     getPower (public)
     setName (public)
     setSpeed (private)
     setMaxSpeed (private)
     setTemperature (private) 
    ```

Para executar as ações do robô

```Java
class Test {
    public static void main(String... args) {
        final Robot robot = new Robot("RobotName");
        robot.wake(); // acordar robô (permite realizar outras ações
        robot.explore();
        robot.back();
        robot.turnOff();
    }
}
```
