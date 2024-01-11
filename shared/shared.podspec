Pod::Spec.new do |spec|
    spec.name                     = 'shared'
    spec.version       = "1.0"
    spec.homepage                 = 'http://google.com'
    spec.source = { :http => "https://github.com/getarchitdhupar/TestCocopodsPublicRepo.git", :tag => "{spec.version}" }
    spec.authors                  = 'getarchit.dhupar@gmail.com'
    spec.license                  = 'https://opensource.org/licenses/Apache-2.0'
    spec.summary                  = 'This is the description of framework example.'

    spec.vendored_frameworks      = "sharedModule.xcframework"
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '16.0'

    spec.ios.deployment_target = '15.0'

end
